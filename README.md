Программа для анализа CSV-файла SportFacilities.

В первую очередь были созданы сущности для хранения информации из файла в виде POJO:
класс City - хранит информацию о городе: название и регион.
класс Facility описывает сооружение: ID в реестре, название, описание, тип, местоположение
класс Work описывает работы производимые над сооружениями: Строение, дата начала работ, дата окончания, финансирование, тип работ

для хранения и формирования объектов был создан FacilitiesData с парсером внутри.

Была спроектирована БД на SQLite из 3 таблиц в 3 нормальной форме:
Таблица городов cities связана внешним ключом с таблицой строений facilities, она же в свою очередь по полю ID связывается с таблицой работ works

Для выполнения запросов и вывода их в консоль была написана утилита DBWorker, который содержал сразу же необходимые методы для извлечения данных по заданию

Для отрисовки графика был написан рисователь Drawer с использованием библиотеки JFreeChart

Результаты выполнения задания вы можете увидеть на следующих скриншотах:
Задание 1:
![image](https://user-images.githubusercontent.com/79924068/147241338-fba0b946-ae01-4a41-9f34-89369ae72363.png)


Задание 2 и 3:
![image](https://user-images.githubusercontent.com/79924068/147241381-9aa6c5db-dd1c-4b31-9cf4-8a8136e2d0ad.png)

