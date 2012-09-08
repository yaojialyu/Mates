from database import SQLAlchemy
from sqlalchemy import Column, String

db = SQLAlchemy("mysql://root:root@127.0.0.1:3306/mates", pool_recycle=3600)

class User(db.Model):
    username = Column(String(16), unique=True, nullable=False)
    password = Column(String(30), nullable=False)

if __name__ == '__main__':
    
    # db.create_db()

    # for i in range(10):
    #     user = User(username="user"+str(i), password="password")
    #     db.session.add(user)

    # db.session.commit()

    users = User.query.filter_by(username__contains="user").all()
    for user in users :
        print user.id, user.username, user.password

    