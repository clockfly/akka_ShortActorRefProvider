Akka remoting AliasActorRefProvider
==================================

The purpose is to reduce the akka message overhead when sending a message across network.

Currently in akka 2.3.4, when you want to send a message across network, it will contains at least three part:

1. Sender ActorRef Path
2. Receiver ActorRef Path
3. Message payload.

For sender and receiver actorRef path, each will take around 100 bytes - 200 bytes for network, this is not trivival network overhead. There is an option to configure not sending sender address, ```actorRef.tell(msg, ActorRef.noSender)```, however, there is no option to short the receiver ActorRef path currently. 


The code here shows a way to hack akka framework to reduce the reciever ActorRef overhead. After the hack, the receiver ActorRef path will only take about 10 bytes.


Check https://groups.google.com/forum/#!topic/akka-user/Pf4lInh8oPc for the background story and discussions.


How to use it?
==================================

1. sbt package to build the jar

2. then, you need to configure in application.conf 

  ```
  akka.actor.provider = "akka.remote.AliasRemoteActorRefProvider"
  ``` 
2. After that, the target actor(the target you want to send message to) need to call 

  ```
  val aliasActorRef = context.provider.asInstanceOf[AliasActorRefProvider].getAliasActorRef
  ``` 
  to get a alias ActorRef.

3. Then the target actor actor(the target you want to send message to) need to pass the aliasActorRef to source actor(which send message), like this

   ```
   source ! aliasActorRef
   ```

4. In source ator, it need to record the aliasActorRef of target actor. When it need to send message to target actor, it need to use this aliasActorRef.

  ```
  aliasActorRef.tell("message", ActorRef.noSender)
  ```

Where it the hacking happen?
==================================
There are two places

1. Hack ```ActorPath.toSerializationFormat``` with ```AliasActorPath```
The latter will only serialze the shortten alias path when transfer through network. For example, the original path may be：``` akka://sytem@ip:port/user/.../myActorName```, 
after shortten, the path looks like ```"s://s<id>"```, the id will map to a unique Actor in current ActorSystem.

2. The second hack lies in ```RemoteActorRefProvider.resolveActorRefWithLocalAddress```. It will use implementation of AliasRemoteActorRefProvider instead.
In the hacked version,``` AliasRemoteActorRefProvider.resolveActorRefWithLocalAddress``` will parse the short version of ActorPath, resolve it to a full ActorPath in current ActorSystem. 





