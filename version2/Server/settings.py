from tornado.options import define, options

#usage: python MatesServer.py --port=80
define("port", default=8000, help="run on the given port", type=int)
define('dbhost', default='127.0.0.1', help="the database host", type=str)
define('dbuser', default='root', help="the username of database", type=str)
define('dbpass', default='root', help="the password of database", type=str)
define('dbport', default='3306', help="the port of Mysql", type=str)
define('dbname', default='mates', help="the database name", type=str)