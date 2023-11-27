def get_chunks(_list: list, chunk_size: int):
    return [_list[i:i + chunk_size] for i in range(0, len(_list), chunk_size)]
