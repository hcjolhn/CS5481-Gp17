import concurrent.futures
import json

from elasticsearch import helpers

from config.config import config
from elastic_search_conn.opensearch_conn import elastic_search_client
from service import os_service

global_success_cnt = 0


def index_news(base_dir: str):
    _chunks = os_service.get_doc_chunk(base_dir)
    multi_thread_handle_news_dir_chunks(base_dir, _chunks)
    print(f"Finished indexing news at {base_dir=}")


def multi_thread_handle_news_dir_chunks(base_dir: str, _chunks: list[list[str]]):
    global global_success_cnt
    i = 0
    with concurrent.futures.ThreadPoolExecutor(max_workers=4) as executor:
        futures = []
        for chunk in _chunks:
            future = executor.submit(bulk_write_news, base_dir, chunk)
            futures.append(future)
        for future in concurrent.futures.as_completed(futures):
            try:
                success_cnt = future.result()
                global_success_cnt = global_success_cnt + success_cnt
                print(f"{global_success_cnt} items succeeded.")
            except Exception as e:
                print(f"Exception caught when multi-thread handle new dir chunks {e}")


def generate_docs(base_dir: str, news_dir_chunks: list[str]):
    for _dir in news_dir_chunks:
        with open(f"{base_dir}/{_dir}", 'r') as file:
            data = json.load(file)
            yield {
                '_index': config.news_index,
                '_source': data
            }


def bulk_write_news(base_dir, news_dir_chunks: list[str]) -> int:
    success_cnt = 0
    for success, info in helpers.parallel_bulk(elastic_search_client, generate_docs(base_dir, news_dir_chunks)):
        if success:
            success_cnt = success_cnt + 1
    return success_cnt
