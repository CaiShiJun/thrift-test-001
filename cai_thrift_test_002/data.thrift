namespace java com.test.netty.lesson9

typedef i16 short;
typedef i32 int;
typedef i64 long;
typedef string String;
typedef bool boolean;

//定义结构
struct Person {
    1: optional String name;
    2: optional int age;
    3: optional boolean isMarried;
}

//定义异常
exception DataException {
    1: optional String message;
    2: optional String callStack;
    3: optional String date;
}

//定义服务
service PersonService {
    Person getPersonByName(1: required String name) throws (1: DataException dataException);
    void savePerson(1: required Person person) throws (1: DataException dataException);
}