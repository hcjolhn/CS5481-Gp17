import os

import helpers.helper
from config.config import config


def get_doc_chunk(_dir: str):
    return helpers.helper.get_chunks(os.listdir(_dir), int(config.chunk_size))
