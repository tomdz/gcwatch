This is a very simple and crude java app that generates a lot of garbage. It can be used to generate sample GC
logging output.

Build it with:
```bash
mvn clean install
```

When invoking the app, you'll want to vary the invocation with different GC settings:

* `-XX:+UseSerialGC` (serial copying collector for young gen and serial mark and sweep compacting collector
   for old gen)
* `-XX:+UseParNewGC` (parallel copying collector for young gen and serial mark and sweep compacting collector
  for old gen)
* `-XX:+UseParallelGC -XX:+UseParallelOldGC` (parallel scavenging collector for young gen
  and parallel scavenge mark & sweep collector for old gen)
* `-XX:+UseConcMarkSweepGC` (parallel copying collector for young gen and concurrent mark & sweep for old gen)
* `-XX:+UseConcMarkSweepGC -XX:-UseParNewGC` (serial copying collector for young gen and concurrent mark & sweep
   for old gen)
* `-XX:+UseG1GC` (G1 collector)

In addition, there are a few options that affect how the concurrent mark and sweel does its job, e.g.:

* `-XX:+CMSIncrementalMode` enables incremental concurrent GC algorithm
* `-XX:-CMSConcurrentMTEnabled` disables parallel (multiple threads) concurrent GC algorithm
* `-XX:-UseCMSCompactAtFullCollection` disables compaction when a full GC occurs

Finally, enabling `-XX:+PrintTenuringDistribution` will affect the logging output of the algorithms.

Sample invocation (concurrent mark & sweep with incremental mode and parallel copying newgen)
```bash
java -server -Xms128m -Xmx512m \
     -verbose:gc -Xloggc:gc-cmsincr-parnew.log \
     -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintTenuringDistribution \
     -XX:-UseConcMarkSweepGC -XX:+CMSIncrementalMode \
     -jar target/gctestapp-<version>.jar
```
