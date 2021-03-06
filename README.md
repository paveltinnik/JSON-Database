# JSON-Database
JSON is a ubiquitous format for exchanging data between web servers and browsers. Its simple design and flexibility make it easy to understand and use in the programming language of your choice. In this project created a client-server application that allows the clients to store their data on the server in JSON format.

In this project the server is your computer. The server keeps the database on the hard drive in the db.json file and update only after setting a new value or deleting one. Server stores the JSON file in the /server/data folder.

When database server becomes very popular, it won’t be able to process a lot of requests because it can only process many requests at a time. 

In this case is provided parallelizing the server's work using executors, so that every request is parsed and handled in a separate executor's task. The main thread should just wait for incoming requests.

For this kind of functionality,  synchronization is needed because all threads will work with the same file. Even after parallelizing, you need to preserve the integrity of the database. Of course, you can't write the file in two separate threads simultaneously, but if no one is currently writing to the file, a lot of threads can read it, and no-one can interrupt the other since no-one is modifying it. This behavior is implemented in java.util.concurrent.locks.ReentrantReadWriteLock class. It allows multiple readers of the resource but only a single writer. Once a writer locks the resource, it waits until all the readers finish reading and only then starts to write. The readers can freely read the file even though other readers locked it, but if the writer locks the file, no readers can read it.

This database is able to store not only strings, but any JSON objects as values.

The key should not only be a string since the user needs to retrieve part of the JSON value. For example, in the code snippet below, the user wants to get only the surname of the person:

    {
        ... ,

        "person": {
            "name": "Adam",
            "surname": "Smith"
        }
        ...
    }

Then, the user should type the full path to this field in a form of a JSON array: ["person", "surname"]. If the user wants to get the full person object, then they should type ["person"]. The user is able to set separate values inside JSON values. For example, it it is possible to set only the surname using a key ["person", "surname"] and any value including another JSON. Moreover, the user is able to set new values inside other JSON values. For example, using a key ["person", "age"] and a value 25, the person object should look like this:

    {
        ... ,

        "person": {
            "name": "Adam",
            "surname": "Smith",
            "age": 25

        }
        ...
    }

If there are no root objects, the server should create them, too. For example, if the database does not have a "person1" key but the user set the value {"id1": 12, "id2": 14} for the key ["person1", "inside1", "inside2"], then the database will have the following structure:

    {
        ... ,
        "person1": {
            "inside1": {
                "inside2" : {
                    "id1": 12,
                    "id2": 14
                }
            }
        },
        ...
    }

The deletion of objects follows the same rules. If a user deletes the object above by the key ["person1", "inside1", "inside2], then only "inside2" is deleted, not "inside1" or "person1". See the example below:

    {
        ... ,
        "person1": {
            "inside1": { }
        }

        ...
    }

To start client you should add arguments to run configurarion. It shoud always contain -t, -k, -v. Also you can  make JSON request from file, where request is stored. Then you need add only -in argument.
Parameter -t is responsible for type of request. It can be set, get, delete and exit.
Parameter -k is responsible for the rey of JSON request. It can be any Strin or JSON array.
Parameter -v is responsible for value of JSON request. It can be any JSON element.
Parameter -in is responsible for the name of file, where the program reads JSON request. It shoud be stored in JSON format.

EXAMPLES:
At first, you need to start server. 

Starting the server:

> java Main
Server started!

There is no need to format JSON in the output.

Starting the clients:

> java Main -t set -k 1 -v "Hello world!"
 
Client started!
Sent: {"type":"set","key":"1","value":"Hello world!"}
Received: {"response":"OK"}

> java Main -in setFile.json 

Client started!
Sent:

    {
       "type":"set",
       "key":"person",
       "value":{
          "name":"Elon Musk",
          "car":{
             "model":"Tesla Roadster",
             "year":"2018"
          },
          "rocket":{
             "name":"Falcon 9",
             "launches":"87"
          }
       }
    }

Received: {"response":"OK"}

> java Main -in updateFile.json 

Client started!
Sent: 
    {"type":"set","key":["person","rocket","launches"],"value":"88"}
Received: {"response":"OK"}

> java Main -in secondGetFile.json 

Client started!
Sent: 
    {"type":"get","key":["person"]}
Received:

    {
       "response":"OK",
       "value":{
          "name":"Elon Musk",
          "car":{
             "model":"Tesla Roadster",
             "year":"2018"
          },
          "rocket":{
             "name":"Falcon 9",
             "launches":"88"
          }
       }
    }

> java Main -in deleteFile.json 

Client started!
Sent: 
    {"type":"delete","key":["person","car","year"]}
Received: {"response":"OK"}

> java Main -in secondGetFile.json 

Client started!
Sent: 
    {"type":"get","key":["person"]}
Received:

    {
       "response":"OK",
       "value":{
          "name":"Elon Musk",
          "car":{
             "model":"Tesla Roadster"
          },
          "rocket":{
             "name":"Falcon 9",
             "launches":"88"
          }
       }
    }

> java Main -t exit 

Client started!
Sent: 
    {"type":"exit"}
Received: {"response":"OK"}
