# This is a sample Python script.
from enums.Task import Task
from tasks import news_index_task


# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.


def run_task(name):
    match name:
        case Task.NEWS_INDEXING.value:
            news_index_task.news_index()


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    print(":hi")
    run_task('news_indexing')

# See PyCharm help at https://www.jetbrains.com/help/pycharm/
