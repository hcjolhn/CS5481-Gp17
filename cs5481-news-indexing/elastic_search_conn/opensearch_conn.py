from elasticsearch import Elasticsearch
from elasticsearch.helpers import bulk

from config.config import config


class ElasticSearch:
    def __init__(self):
        self.client = Elasticsearch(
            cloud_id=config.es_cloud_id, api_key=config.es_api_key
        )

    def bulk_write(self, docs: list[dict]):
        success, _ = bulk(self.client, docs)


elastic_search = ElasticSearch()
elastic_search_client = elastic_search.client
