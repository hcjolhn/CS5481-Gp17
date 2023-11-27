import os

from dotenv import load_dotenv

load_dotenv()


class Config:
    elastic_username = os.environ.get("elastic_username")
    elastic_password = os.environ.get("elastic_password")
    elastic_host = os.environ.get("elastic_host")
    elastic_port = os.environ.get("elastic_port")
    cnn_dir = os.environ.get("cnn_dir")
    nbc_dir = os.environ.get("nbc_dir")
    news_index = os.environ.get("news_index")
    es_cloud_id = os.environ.get("es_cloud_id")
    es_api_key = os.environ.get("es_api_key")
    chunk_size = os.environ.get("chunk_size")
    daily_dir = os.environ.get("daily_dir")
    bbc_dir = os.environ.get("bbc_dir")
    dailymail2322_dir = os.environ.get("dailymail2322_dir")


config = Config()
