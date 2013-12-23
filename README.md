# GC logging

GC logging is turned on with `-verbose:gc`. By default that prints to stdout, but can be redirected via `-Xloggc:<path>`

You will see output like:
```
[GC 32039K->31104K(125632K), 0.0523080 secs]
[GC 63476K->61792K(158400K), 0.0419570 secs]
[Full GC 61792K->61737K(201728K), 0.0152370 secs]
[GC 126698K->126314K(201728K), 0.0819170 secs]
```

A lot more detail can be seen when `-XX:+PrintGCDetails` is added to the java commandline, e.g.
```
[GC [PSYoungGen: 32039K->4480K(38208K)] 32039K->31104K(125632K), 0.0396310 secs]
 [Times: user=0.04 sys=0.03, real=0.04 secs]
[GC [PSYoungGen: 36852K->4448K(70976K)] 63476K->61792K(158400K), 0.0451220 secs]
 [Times: user=0.03 sys=0.03, real=0.04 secs]
[Full GC [PSYoungGen: 4448K->0K(70976K)] [ParOldGen: 57344K->61737K(130752K)] 61792K->61737K(201728K)
 [PSPermGen: 2714K->2712K(21248K)], 0.0139930 secs] [Times: user=0.02 sys=0.01, real=0.02 secs]
[GC [PSYoungGen: 64961K->5184K(70976K)] 126698K->126314K(201728K), 0.0804900 secs]
 [Times: user=0.06 sys=0.06, real=0.09 secs]
```

Also useful is the `-XX:+PrintGCTimeStamps` option which adds a timestamp showing the time since start of the VM:
```
0.438: [GC [PSYoungGen: 32039K->4480K(38208K)] 32039K->31104K(125632K), 0.0390920 secs]
 [Times: user=0.03 sys=0.03, real=0.04 secs]
0.819: [GC [PSYoungGen: 36852K->4480K(70976K)] 63476K->61824K(158400K), 0.0440640 secs]
 [Times: user=0.03 sys=0.03, real=0.04 secs]
0.863: [Full GC [PSYoungGen: 4480K->0K(70976K)] [ParOldGen: 57344K->61737K(131200K)] 61824K->61737K(202176K)
 [PSPermGen: 2714K->2712K(21248K)], 0.0154270 secs] [Times: user=0.02 sys=0.01, real=0.02 secs]
1.592: [GC [PSYoungGen: 64961K->5184K(70976K)] 126698K->126314K(202176K), 0.0735130 secs]
 [Times: user=0.07 sys=0.06, real=0.07 secs]
```
Likewise `-XX:+PrintGCDateStamps` prints absolute timestamps instead of relative ones:
```
2013-09-28T22:03:57.210+0800: [GC [PSYoungGen: 32039K->4480K(38208K)] 32039K->31104K(125632K), 0.0674540 secs]
 [Times: user=0.06 sys=0.03, real=0.06 secs]
2013-09-28T22:03:57.609+0800: [GC [PSYoungGen: 36852K->4480K(70976K)] 63476K->61824K(158400K), 0.0409750 secs]
 [Times: user=0.04 sys=0.03, real=0.05 secs]
2013-09-28T22:03:57.650+0800: [Full GC [PSYoungGen: 4480K->0K(70976K)] [ParOldGen: 57344K->61737K(130112K)]
 61824K->61737K(201088K) [PSPermGen: 2714K->2712K(21248K)], 0.0142200 secs]
 [Times: user=0.01 sys=0.01, real=0.01 secs]
2013-09-28T22:03:58.389+0800: [GC [PSYoungGen: 64961K->5184K(70976K)] 126698K->126314K(201088K), 0.0775760 secs]
 [Times: user=0.07 sys=0.06, real=0.08 secs]
```

In addition, the JVM can be asked to print out the tenuring distribution via `-XX:+PrintTenuringDistribution`:
```
[GC [ParNew
Desired survivor size 2228224 bytes, new threshold 15 (max 15)
- age   1:        296 bytes,        296 total
- age   2:        264 bytes,        560 total
- age   3:        120 bytes,        680 total
- age   4:        288 bytes,        968 total
- age   5:     310880 bytes,     311848 total
: 18826K->533K(39296K), 0.0268780 secs][Tenured: 103973K->104278K(105468K), 0.0059870 secs]
 104759K->104278K(144764K), [Perm : 2872K->2872K(21248K)], 0.0360130 secs]
 [Times: user=0.04 sys=0.01, real=0.04 secs]
```
and the distribution of the heap with `-XX:+PrintHeapAtGC`:
```
{Heap before GC invocations=9 (full 4):
 par new generation   total 157248K, used 136433K [0x00000007dae00000, 0x00000007e58a0000, 0x00000007e58a0000)
  eden space 139776K,  97% used [0x00000007dae00000, 0x00000007e333c6a8, 0x00000007e3680000)
  from space 17472K,   0% used [0x00000007e4790000, 0x00000007e4790000, 0x00000007e58a0000)
  to   space 17472K,   0% used [0x00000007e3680000, 0x00000007e3680000, 0x00000007e4790000)
 tenured generation   total 349568K, used 332963K [0x00000007e58a0000, 0x00000007fae00000, 0x00000007fae00000)
   the space 349568K,  95% used [0x00000007e58a0000, 0x00000007f9dc8dd0, 0x00000007f9dc8e00, 0x00000007fae00000)
 compacting perm gen  total 21248K, used 2870K [0x00000007fae00000, 0x00000007fc2c0000, 0x0000000800000000)
   the space 21248K,  13% used [0x00000007fae00000, 0x00000007fb0cdaf8, 0x00000007fb0cdc00, 0x00000007fc2c0000)
No shared spaces configured.
[GC [ParNew: 136433K->136433K(157248K), 0.0000210 secs][Tenured: 332963K->323185K(349568K), 0.1429500 secs]
 469397K->323185K(506816K), [Perm : 2870K->2870K(21248K)], 0.1430300 secs]
 [Times: user=0.14 sys=0.00, real=0.14 secs]
Heap after GC invocations=10 (full 5):
 par new generation   total 157248K, used 0K [0x00000007dae00000, 0x00000007e58a0000, 0x00000007e58a0000)
  eden space 139776K,   0% used [0x00000007dae00000, 0x00000007dae00000, 0x00000007e3680000)
  from space 17472K,   0% used [0x00000007e4790000, 0x00000007e4790000, 0x00000007e58a0000)
  to   space 17472K,   0% used [0x00000007e3680000, 0x00000007e3680000, 0x00000007e4790000)
 tenured generation   total 349568K, used 323185K [0x00000007e58a0000, 0x00000007fae00000, 0x00000007fae00000)
   the space 349568K,  92% used [0x00000007e58a0000, 0x00000007f943c510, 0x00000007f943c600, 0x00000007fae00000)
 compacting perm gen  total 21248K, used 2870K [0x00000007fae00000, 0x00000007fc2c0000, 0x0000000800000000)
   the space 21248K,  13% used [0x00000007fae00000, 0x00000007fb0cdaf8, 0x00000007fb0cdc00, 0x00000007fc2c0000)
No shared spaces configured.
}
```
As you can see, `-XX:+PrintHeapAtGC` makes the output very verbose.

# Supported log statements

Most of the following log statements look something like:
```
0.148 <log statement> [Times: user=0.02 sys=0.01, real=0.04 secs]
```
The first value is the time since the application was started (this assumes `-XX:+PrintGCTimeStamps` was used).
The `Times` section at the end prints execution time in user (application) and system (kernel) space as well as
real time (wall clock time), similar to the `time(1)` command.

### Serial copying collector (`UseSerialGC`)

##### Young generation

```
0.148: [GC 0.148: [DefNew: 32443K->334K(39296K), 0.0383600 secs] 32443K->25080K(126720K),
 0.0384220 secs] [Times: user=0.02 sys=0.01, real=0.04 secs]
```
A young generation collection happened at `0.148` seconds after the application started. The young generation is `39296K`
big, and `32443K` of it was occupied before and `334K` after the collection. The young generation collection took
`0.0383600` seconds. The total available size of young and old generation is `126720K`, and before the young generation
collection `32443K` were occupied (i.e. all objects were in the young generation), and after the collection `25080K`
were occupied. The whole collection took `0.0384220` sconds.

With tenuring distribution logging this log output changes to:
```
0.280: [GC 0.280: [DefNew
Desired survivor size 2228224 bytes, new threshold 15 (max 15)
- age   1:         56 bytes,         56 total
- age   2:         56 bytes,        112 total
- age   3:         40 bytes,        152 total
- age   4:        536 bytes,        688 total
- age   5:     314800 bytes,     315488 total
: 20473K->308K(39296K), 0.0282400 secs]0.309: [Tenured: 105619K->105927K(107592K), 0.0054030 secs]
 105927K->105927K(146888K), [Perm : 2872K->2872K(21248K)], 0.0340160 secs]
 [Times: user=0.02 sys=0.01, real=0.04 secs]
```
See the parallel copying collector below for details on this.


```
0.633: [GC 0.633: [DefNew (promotion failed) : 136158K->149171K(157248K), 0.1441370 secs]0.777:
 [Tenured: 333101K->327922K(349568K), 0.0983430 secs] 370626K->327922K(506816K),
 [Perm : 2872K->2872K(21248K)], 0.2425880 secs] [Times: user=0.18 sys=0.07, real=0.24 secs]
```

##### Old generation

```
2.012: [Full GC 2.012: [Tenured: 343656K->216196K(349568K), 0.0886910 secs]
 492212K->216196K(506816K), [Perm : 2870K->2870K(21248K)], 0.0887540 secs]
 [Times: user=0.09 sys=0.00, real=0.09 secs]
```
This is the logging for the serial mark and sweep collector. The collection happened at `2.012` seconds after the
application was started. It reduced the old generation from `343656K` to `216196K` (out of a max of `349568K`) and
took `0.0886910` seconds for the old generation part.
It also affected the young generation. Even though it prints no explicit info about it, we can see that it reduced
total used young and old generation space from `492212K` to `216196K` (with a max available of `506816K`). This is the
same as the size of the tenured space after the collection which means that the young generation space is empty after
the collection.
Finally, it tried to unsucessfully collect in the permanent generation, but the used size stayed at `2870K` (out
of `21248K` max).
The whole collection took `0.0887540` seconds.

A `(System)` after `Full GC` would indicate that the collection was requested by the application (i.e. `System.gc()`).

### Parallel copying collector (`UseParNewGC`)

##### Young generation

```
0.130: [GC 0.130: [ParNew: 24187K->342K(39296K), 0.0275930 secs] 24187K->16133K(126720K),
 0.0276490 secs] [Times: user=0.03 sys=0.01, real=0.03 secs]
```
A young generation collection happened at `1.030` seconds after the application started. The young generation is `39296K`
big, and `24187K` of it was occupied before and `342K` after the collection. The young generation collection took
`0.0275930` seconds. The total available size of young and old generation is `126720K`, and before the young generation
collection `24187K` were occupied (i.e. all objects were in the young generation), and after the collection `16133K`
were occupied (i.e. `15791K` = `16133K` - `342K` were promoted to the old generation). The whole collection took
`0.0276490` sconds.

Similarly, with the tenuring distribution:
```
0.245: [GC 0.245: [ParNew
Desired survivor size 2228224 bytes, new threshold 15 (max 15)
- age   1:        392 bytes,        392 total
- age   2:         56 bytes,        448 total
- age   3:        256 bytes,        704 total
- age   4:     311144 bytes,     311848 total
: 29587K->451K(39296K), 0.0349430 secs]0.280: [Tenured: 97630K->80633K(104540K),
 0.0371550 secs] 98793K->80633K(143836K), [Perm : 2872K->2872K(21248K)], 0.0723530 secs]
 [Times: user=0.08 sys=0.03, real=0.08 secs]
```
In addition to the above, this prints out statistics about the survivor spaces. The 'desired survivor size' which is
the target utilization of the survivor space, is `2228224` bytes. This is calculated as
`NewSize`*`TargetSurvivorRatio`/`(SurvivorRatio + 2)` (because new size = eden size + 2*survivor size, and eden size
= survivor ratio * survivor size).
The configured `MaxTenuringThreshold`, which is the maximum number of times that the young generation collector
will copy objects between the two survivor spaces before tenuring them, is `15` (value in parentheses). The current
tenuring threshold is also `15` which means that during the next collection, no object will be forced out of the
survivor spaces (in order to make room in the new generation).
Next, it prints the sizes of objects at the various ages (number of times that the objects have been copied to a
survivor space). The first value is the size at that age (e.g. `392` bytes for age 1, `56` bytes for age 2, ...),
and the second is the running total (e.g. `448` bytes is the sum of age 1 and 2). If there are no objects at a specific
age, then there won't be a line for it in the output.

The output also contains a old generation collection (`[Tenured ...]`) and a permanent generation collection
(`[Perm ...]`). The tenured space was reduced from `97630K` to `80633K` (out of `104540K` total), and
permanent generation space stayed the same at `2872K`.

```
5.031: [GC 5.031: [ParNew (promotion failed): 132163K->146998K(157248K), 0.0405880 secs]5.071:
 [Tenured: 335614K->284433K(349568K), 0.1145870 secs] 382197K->284433K(506816K),
 [Perm : 2870K->2870K(21248K)], 0.1552900 secs] [Times: user=0.19 sys=0.00, real=0.16 secs]
```
or with tenuring distribution output:
```
1.203: [GC 1.203: [ParNew (promotion failed)
Desired survivor size 8945664 bytes, new threshold 1 (max 15)
- age   1:   15952216 bytes,   15952216 total
: 135327K->150909K(157248K), 0.0844020 secs]1.287: [Tenured: 349433K->303009K(349568K),
 0.1095430 secs] 383828K->303009K(506816K), [Perm : 2870K->2870K(21248K)], 0.1940660 secs]
 [Times: user=0.23 sys=0.04, real=0.20 secs]
```
The `promotion failed` here means that a young generation collection was requested but not attempted. In the above
example, the last GC moved `15952216` bytes from eden to the survivor space. This is bigger than the target survivor
size of `8945664` bytes, so the young generation collector marked age 1 (and above) for tenuring in the next GC
(`new threshold 1`). However, the tenured space is almost full (`349433K` of `349568K`), so this promotion (tenuring
of young generation data) is aborted, and there is most likely a full stop-the-world GC right after this in the logs.


##### Old generation

```
0.954: [Full GC 0.954: [Tenured: 338420K->337591K(349568K), 0.1746320 secs]
 472544K->429640K(506816K), [Perm : 2872K->2870K(21248K)], 0.1747090 secs]
 [Times: user=0.17 sys=0.00, real=0.17 secs]
```
Same as for the serial copying collector, this uses the serial mark and sweep collector for the old generation.

### Parallel scavenge & mark and sweep collectors (`UseParallelGC` + `UseParallelOldGC`)

##### Young generation

```
0.110: [GC [PSYoungGen: 32768K->384K(38208K)] 32768K->26662K(125632K), 0.0317620 secs]
 [Times: user=0.04 sys=0.03, real=0.03 secs]
```
This is the output for a young generation collection at `0.110` seconds application lifetime that reduced the young
generation size from `32768K` to `384K` (out of a `38208K` maximum). The combined used young and old generation size
went from `32768K` (i.e. all young generation) to `26662K` (with a maximum available of `125632K`). The collection
took `0.0317620` seconds.

```
0.223: [GC
Desired survivor size 5570560 bytes, new threshold 7 (max 15)
 [PSYoungGen: 29285K->416K(38208K)] 187914K->181767K(239104K), 0.0342700 secs]
 [Times: user=0.04 sys=0.01, real=0.04 secs]
```
With `-XX:+PrintTenuringDistribution` you get output similar to above with some additional survivor stats. For some
reason, it won't output the tenuring distribution though.


```
2.986: [GC-- [PSYoungGen: 32089K->32089K(38208K)] 343582K->381463K(387776K),
 0.0155910 secs] [Times: user=0.03 sys=0.00, real=0.01 secs]
```
This shows an attempted young generation collection that failed mid-way (promotion failure) because it ran out of
space to copy surviors from the young generation into either survivor or tenured space. The increase in heap usage
(`343582K` to `381463K`) here is an artifact of how the operation is unwound. Some objects got copied into survivor/old
generation space, but the young generation size is not adjusted accordingly.

##### Old generation

```
0.186: [Full GC [PSYoungGen: 29813K->13540K(38208K)] [ParOldGen: 64141K->77375K(87424K)]
 93955K->90916K(125632K) [PSPermGen: 2872K->2870K(21248K)], 0.0321400 secs]
 [Times: user=0.03 sys=0.01, real=0.03 secs]
```
A full collection of both young generation (bringing it down from `29813K` of `38208K` max to `13540K`) and old
generation (which changes from `64141K` of `87424K` max to `77375K` due to promotion of some objects from the young
generation). Young and old generation combined changed from `93955K` of `125632K` max, to `90916K`.
In addition, this also ran a collection in the permanent generation, reducing the used space from `2872K` (max
`21248K`) to `2870K`.
The whole collection took `0.0321400` seconds.

### Concurrent mark & sweep (`UseConcMarkSweepGC`)

This uses the serial copying collector from above for the young generation.

##### Young generation

For young generation see the young generation section of the serial copying collector above. However, the output can be
slightly different depending on when the young generation collection happens, or if there are errors:
```
0.466: [GC 0.466: [DefNew0.552: [CMS-concurrent-abortable-preclean: 0.002/0.103 secs]
 [Times: user=0.07 sys=0.04, real=0.11 secs]
```
```
0.624: [GC 0.624: [DefNew0.693: [CMS-concurrent-abortable-preclean: 0.004/0.318 secs]
 [Times: user=0.24 sys=0.10, real=0.32 secs]
: 67688K->304K(76672K), 0.0908210 secs] 303659K->302504K(379460K), 0.0908870 secs]
 [Times: user=0.06 sys=0.03, real=0.09 secs]
```
```
1.315: [GC 1.315: [DefNew: 58217K->58217K(76672K), 0.0000210 secs]1.315:
 [CMS (concurrent mode failure): 434267K->295157K(439104K), 0.1355210 secs] 492484K->295157K(515776K),
 [CMS Perm : 2871K->2871K(21248K)], 0.1356270 secs] [Times: user=0.13 sys=0.00, real=0.14 secs]
```
```
2.977: [GC 2.977: [DefNew: 58230K->58230K(76672K), 0.0000320 secs]2.977: [CMS2.977:
 [CMS-concurrent-abortable-preclean: 0.003/0.115 secs] [Times: user=0.12 sys=0.00, real=0.11 secs]
 (concurrent mode failure): 399693K->119158K(439104K), 0.0542350 secs]
 457923K->119158K(515776K), [CMS Perm : 2871K->2871K(21248K)], 0.0543650 secs]
 [Times: user=0.06 sys=0.00, real=0.06 secs]
```

##### Old generation

```
0.333: [GC [1 CMS-initial-mark: 104881K(108784K)] 150075K(185456K), 0.0006170 secs]
 [Times: user=0.00 sys=0.00, real=0.00 secs]
```
This indicates the start of the CMS collection. The initial mark phase is a stop-the-world phase that marks all objects
directly reachable from GC roots. `104881K` is the amount of used old generation space, out of `108784K` max available.
`150075K` is the combined size of used young and old generation and `185456K` the total size of young and old generation
combined. The phase took `0.0006170` seconds.

```
0.334: [CMS-concurrent-mark-start]
```
This indicates the start of the concurrent marking phase in which all objects that are reachable transitively from the
initial mark set are marked as well. This phase is not stop-the-world.

```
0.447: [CMS-concurrent-mark: 0.028/0.113 secs]
 [Times: user=0.08 sys=0.05, real=0.11 secs]
```
Marks the end of the concurrent mark phase with an elapsed time of `0.028` seconds and `0.113` wall clock time since
the start of the phase.

```
0.447: [CMS-concurrent-preclean-start]
```
Marks the start of the concurrent preclean phase in which all objects in the old generation are rescanned that were
promoted from the young generation or updated by mutated during the concurrent mark phase (grey objects). This helps
reduce work in the CMS remark phase.

```
0.448: [CMS-concurrent-preclean: 0.001/0.001 secs]
 [Times: user=0.01 sys=0.00, real=0.00 secs]
```
The end of the concurrent preclean phase which took `0.001` seconds elapsed and wall clock time.

```
0.448: [CMS-concurrent-abortable-preclean-start]
```
Marks the start of the concurrent abortable preclean phase. This phase is scheduled to avoid back-to-back runs of
the CMS remark phase and young generation collection (which would introduce longer application pauses). The goal is
to schedule the remark phase roughly mid-way between young generation collections, and so abortable preclean is run
until we have the desired eden occupancy (usually <= 50% occupancy). In addition, abortable preclean also rescans
root and grey objects just like the preclean phase.

```
1.128: [CMS-concurrent-abortable-preclean: 0.000/0.000 secs]
 [Times: user=0.00 sys=0.00, real=0.00 secs]
```
The end of the abortable preclean phase which took `0` seconds elapsed/wall time.

```
0.558: [GC[YG occupancy: 14868 K (76672 K)]0.558: [Rescan (non-parallel)
 0.558: [grey object rescan, 0.0015300 secs]0.560: [root rescan, 0.0004070 secs],
 0.0019620 secs]0.560: [weak refs processing, 0.0000060 secs]0.560:
 [scrub string table, 0.0000200 secs] [1 CMS-remark: 230149K(234076K)] 245017K(310748K),
 0.0020560 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
```
Remark phase. This is a stop-the-world phase that rescans the old generation for any updated objects (`0.0015300`
seconds) and retrace them from GC roots (`0.0004070` seconds), as well as process weak reference objects
(`0.0000060` seconds) and possibly other things (e.g. 'scrub string table' for `0.0000200` seconds).
After the remark phase which took `0.0020560` seconds total, the young generation occupancy is `14868K` out of
`76672K` max, and the old generation occupancy is `230149K` out of `234076K` max, for a total young and old
generation occupancy of `245017K` out of `310748K`.

```
0.573: [CMS-concurrent-sweep-start]
```
Start of the concurrent sweep phase (non-stop-the-world) which clears all not-marked objects from the heap.

```
0.573: [CMS-concurrent-sweep: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
```
Concurrent sweep phase has finished after `0.000` seconds.

```
0.575: [CMS-concurrent-reset-start]
```
Marks the start of the concurrent reset phase which is used to reset CMS data structures for the next CMS
cycle.

```
0.579: [CMS-concurrent-reset: 0.005/0.005 secs] [Times: user=0.02 sys=0.00, real=0.01 secs]
```
Marks the end of the concurrent reset phase after `0.005` seconds elasped and wall-clock time.

```
1.401: [Full GC 1.401: [CMS: 435854K->420323K(439104K), 0.1724540 secs] 491601K->441614K(515776K),
 [CMS Perm : 2871K->2871K(21248K)], 0.1725150 secs] [Times: user=0.17 sys=0.00, real=0.17 secs]
```
This shows a full serial mark and sweep collection (not CMS) which dropped old generation occupancy
from `435854K` (out of `439104K` max) to `420323K` in `0.1724540` seconds and consequently young and
old generation occupancy from `491601K` (out of `515776K` max) to `441614K`. It also attempted a
collection in the permanent generation but without success (stayed at `2871K` out of `21248K` max)
which took `0.1725150` seconds.

```
4.019: [Full GC 4.019: [CMS4.024: [CMS-concurrent-mark: 0.005/0.005 secs]
 [Times: user=0.00 sys=0.00, real=0.00 secs]
 (concurrent mode failure): 433603K->433603K(439104K), 0.1609100 secs] 501763K->501573K(515776K),
 [CMS Perm : 2871K->2871K(21248K)], 0.1609830 secs] [Times: user=0.16 sys=0.00, real=0.16 secs]
```
```
6.480: [Full GC 6.480: [CMS6.480: [CMS-concurrent-abortable-preclean: 0.003/0.171 secs]
 [Times: user=0.19 sys=0.00, real=0.17 secs]
 (concurrent mode failure): 390440K->120556K(439104K), 0.0542700 secs] 440571K->120556K(515776K),
 [CMS Perm : 2871K->2871K(21248K)], 0.0543300 secs] [Times: user=0.06 sys=0.01, real=0.05 secs]
```
A full serial mark and sweep collection (not CMS) was triggered by a `concurrent mode failure` which
indicates that CMS can't keep up. The first of these examples showed an unsuccessful full GC (no
change in old generation occupancy which stayed at `501763K`). The second example shows a successful
full GC (old generation occupancy `440571K` down to `120556K`).
This can happen in various CMS phases (`CMS-concurrent-mark`, `CMS-concurrent-preclean`,
`CMS-concurrent-abortable-preclean`, `CMS-concurrent-sweep`, etc.).

### Concurrent mark & sweep with parallel copying collector (`UseConcMarkSweepGC` + `UseParNew`)

##### Young generation

Similar to CMS with the serial copy collector, the young generation output is mostly the same as for the
parallel copy collector, except in case of errors:
```
1.153: [GC 1.153: [ParNew (promotion failed): 134560K->149517K(153344K), 0.0597560 secs]1.212:
 [CMS: 336462K->336575K(353920K), 0.1366550 secs] 378938K->336575K(507264K),
 [CMS Perm : 2869K->2869K(21248K)], 0.1965180 secs] [Times: user=0.22 sys=0.02, real=0.20 secs]
```
```
1.603: [GC 1.603: [ParNew (promotion failed): 128159K->138500K(153344K), 0.0580050 secs]1.661:
 [CMS1.661: [CMS-concurrent-abortable-preclean: 0.001/0.080 secs]
 [Times: user=0.12 sys=0.01, real=0.08 secs]
 (concurrent mode failure): 343006K->307673K(353920K), 0.1365140 secs] 386414K->307673K(507264K),
 [CMS Perm : 2871K->2871K(21248K)], 0.1946250 secs] [Times: user=0.21 sys=0.02, real=0.20 secs]
```
```
2.712: [GC 2.712: [ParNew (promotion failed)
Desired survivor size 8716288 bytes, new threshold 1 (max 6)
- age   1:   13959112 bytes,   13959112 total
: 130873K->144509K(153344K), 0.0427680 secs]2.755: [CMS2.755: [CMS-concurrent-abortable-preclean:
 0.020/0.102 secs] [Times: user=0.14 sys=0.00, real=0.11 secs]
 (concurrent mode failure): 347072K->130560K(353920K), 0.0588300 secs] 386044K->130560K(507264K),
 [CMS Perm : 2871K->2871K(21248K)], 0.1017550 secs] [Times: user=0.14 sys=0.00, real=0.11 secs]
```
```
7.830: [GC 7.830: [ParNew7.880: [CMS-concurrent-abortable-preclean: 0.001/0.102 secs]
 [Times: user=0.18 sys=0.00, real=0.10 secs]
 (promotion failed)
Desired survivor size 8716288 bytes, new threshold 1 (max 6)
- age   1:   11090800 bytes,   11090800 total
: 146845K->144998K(153344K), 0.0552820 secs]7.885: [CMS (concurrent mode failure):
 323907K->202349K(353920K), 0.0380160 secs] 347427K->202349K(507264K),
 [CMS Perm : 2871K->2871K(21248K)], 0.0934090 secs] [Times: user=0.15 sys=0.00, real=0.09 secs]
```

```
0.556: [GC[YG occupancy: 20862 K (39296 K)]0.556: [Rescan (parallel) , 0.0004730 secs]0.556:
 [weak refs processing, 0.0000090 secs]0.556: [scrub string table, 0.0000210 secs]
 [1 CMS-remark: 230825K(241908K)] 251688K(281204K), 0.0006140 secs]
 [Times: user=0.00 sys=0.00, real=0.00 secs]
```

### G1 (`UseG1GC`)

```
0.133: [GC pause (young)
Desired survivor size 2097152 bytes, new threshold 15 (max 15)
 (initial-mark), 0.00317600 secs]
   [Parallel Time:   2.9 ms]
      [GC Worker Start (ms):  133.1  133.2
       Avg: 133.1, Min: 133.1, Max: 133.2, Diff:   0.2]
      [Ext Root Scanning (ms):  1.3  1.2
       Avg:   1.2, Min:   1.2, Max:   1.3, Diff:   0.1]
      [Update RS (ms):  0.0  0.0
       Avg:   0.0, Min:   0.0, Max:   0.0, Diff:   0.0]
         [Processed Buffers : 2 9
          Sum: 11, Avg: 5, Min: 2, Max: 9, Diff: 7]
      [Scan RS (ms):  0.0  0.0
       Avg:   0.0, Min:   0.0, Max:   0.0, Diff:   0.0]
      [Object Copy (ms):  1.2  1.2
       Avg:   1.2, Min:   1.2, Max:   1.2, Diff:   0.0]
      [Termination (ms):  0.0  0.0
       Avg:   0.0, Min:   0.0, Max:   0.0, Diff:   0.0]
         [Termination Attempts : 1 1
          Sum: 2, Avg: 1, Min: 1, Max: 1, Diff: 0]
      [GC Worker End (ms):  135.9  135.9
       Avg: 135.9, Min: 135.9, Max: 135.9, Diff:   0.0]
      [GC Worker (ms):  2.9  2.7
       Avg:   2.8, Min:   2.7, Max:   2.9, Diff:   0.2]
      [GC Worker Other (ms):  0.5  0.5
       Avg:   0.5, Min:   0.5, Max:   0.5, Diff:   0.0]
   [Clear CT:   0.0 ms]
   [Other:   0.2 ms]
      [Choose CSet:   0.0 ms]
      [Ref Proc:   0.1 ms]
      [Ref Enq:   0.0 ms]
      [Free CSet:   0.0 ms]
   [Eden: 1024K(25M)->0B(24M) Survivors: 0B->1024K Heap: 40M(128M)->39M(128M)]
 [Times: user=0.00 sys=0.00, real=0.01 secs]
```
```
0.136: [GC concurrent-root-region-scan-start]
```
```
0.137: [GC concurrent-root-region-scan-end, 0.0005950]
```
```
0.137: [GC concurrent-mark-start]
```
```
0.155: [GC concurrent-mark-end, 0.0178980 sec]
```
```
0.473: [GC concurrent-mark-abort]
```
```
0.173: [GC remark 0.173: [GC ref-proc, 0.0000120 secs], 0.0011330 secs]
 [Times: user=0.00 sys=0.00, real=0.00 secs]
```
```
0.261: [GC cleanup 206M->206M(215M), 0.0002860 secs]
 [Times: user=0.00 sys=0.00, real=0.00 secs]
```
```
0.451: [Full GC 476M->456M(512M), 0.0114940 secs]
 [Times: user=0.01 sys=0.00, real=0.01 secs]
```

### Others

##### `PrintHeapAtGC`

The output of this depends on which GC algorithm(s) are used.

* Serial collector
```
{Heap before GC invocations=5 (full 1):
 def new generation   total 79552K, used 65226K [0x0000000113540000, 0x0000000118b90000, 0x000000011dfe0000)
  eden space 70720K,  92% used [0x0000000113540000, 0x00000001174f2aa0, 0x0000000117a50000)
  from space 8832K,   0% used [0x0000000117a50000, 0x0000000117a50000, 0x00000001182f0000)
  to   space 8832K,   0% used [0x00000001182f0000, 0x00000001182f0000, 0x0000000118b90000)
 tenured generation   total 176548K, used 105927K [0x000000011dfe0000, 0x0000000128c49000, 0x0000000133540000)
   the space 176548K,  59% used [0x000000011dfe0000, 0x0000000124751fa0, 0x0000000124752000, 0x0000000128c49000)
 compacting perm gen  total 21248K, used 2872K [0x0000000133540000, 0x0000000134a00000, 0x0000000138740000)
   the space 21248K,  13% used [0x0000000133540000, 0x000000013380e2f0, 0x000000013380e400, 0x0000000134a00000)
No shared spaces configured.
```
and
```
Heap after GC invocations=6 (full 1):
 def new generation   total 79552K, used 0K [0x0000000113540000, 0x0000000118b90000, 0x000000011dfe0000)
  eden space 70720K,   0% used [0x0000000113540000, 0x0000000113540000, 0x0000000117a50000)
  from space 8832K,   0% used [0x00000001182f0000, 0x00000001182f0080, 0x0000000118b90000)
  to   space 8832K,   0% used [0x0000000117a50000, 0x0000000117a50000, 0x00000001182f0000)
 tenured generation   total 176548K, used 171154K [0x000000011dfe0000, 0x0000000128c49000, 0x0000000133540000)
   the space 176548K,  96% used [0x000000011dfe0000, 0x00000001287049c0, 0x0000000128704a00, 0x0000000128c49000)
 compacting perm gen  total 21248K, used 2872K [0x0000000133540000, 0x0000000134a00000, 0x0000000138740000)
   the space 21248K,  13% used [0x0000000133540000, 0x000000013380e2f0, 0x000000013380e400, 0x0000000134a00000)
No shared spaces configured.
}
```

* Parallel copying collector
```
{Heap before GC invocations=4 (full 1):
 par new generation   total 60544K, used 46440K [0x00000001048b0000, 0x0000000108a60000, 0x000000010f350000)
  eden space 53824K,  86% used [0x00000001048b0000, 0x000000010760a3b0, 0x0000000107d40000)
  from space 6720K,   0% used [0x0000000107d40000, 0x0000000107d40000, 0x00000001083d0000)
  to   space 6720K,   0% used [0x00000001083d0000, 0x00000001083d0000, 0x0000000108a60000)
 tenured generation   total 134392K, used 80633K [0x000000010f350000, 0x000000011768e000, 0x00000001248b0000)
   the space 134392K,  59% used [0x000000010f350000, 0x000000011420e6a8, 0x000000011420e800, 0x000000011768e000)
 compacting perm gen  total 21248K, used 2872K [0x00000001248b0000, 0x0000000125d70000, 0x0000000129ab0000)
   the space 21248K,  13% used [0x00000001248b0000, 0x0000000124b7e2f0, 0x0000000124b7e400, 0x0000000125d70000)
No shared spaces configured.
```
and
```
Heap after GC invocations=5 (full 1):
 par new generation   total 60544K, used 303K [0x00000001048b0000, 0x0000000108a60000, 0x000000010f350000)
  eden space 53824K,   0% used [0x00000001048b0000, 0x00000001048b0000, 0x0000000107d40000)
  from space 6720K,   4% used [0x00000001083d0000, 0x000000010841be70, 0x0000000108a60000)
  to   space 6720K,   0% used [0x0000000107d40000, 0x0000000107d40000, 0x00000001083d0000)
 tenured generation   total 134392K, used 126710K [0x000000010f350000, 0x000000011768e000, 0x00000001248b0000)
   the space 134392K,  94% used [0x000000010f350000, 0x0000000116f0d9d8, 0x0000000116f0da00, 0x000000011768e000)
 compacting perm gen  total 21248K, used 2872K [0x00000001248b0000, 0x0000000125d70000, 0x0000000129ab0000)
   the space 21248K,  13% used [0x00000001248b0000, 0x0000000124b7e2f0, 0x0000000124b7e400, 0x0000000125d70000)
No shared spaces configured.
}
```

* Parallel scavening/mark and sweep collectors
```
{Heap before GC invocations=2 (full 1):
 PSYoungGen      total 38208K, used 29813K [0x00000007f5560000, 0x00000007f8000000, 0x0000000800000000)
  eden space 32768K, 89% used [0x00000007f5560000,0x00000007f721d570,0x00000007f7560000)
  from space 5440K, 7% used [0x00000007f7560000,0x00000007f75c0000,0x00000007f7ab0000)
  to   space 5440K, 0% used [0x00000007f7ab0000,0x00000007f7ab0000,0x00000007f8000000)
 ParOldGen       total 87424K, used 64141K [0x00000007e0000000, 0x00000007e5560000, 0x00000007f5560000)
  object space 87424K, 73% used [0x00000007e0000000,0x00000007e3ea3728,0x00000007e5560000)
 PSPermGen       total 21248K, used 2872K [0x00000007dae00000, 0x00000007dc2c0000, 0x00000007e0000000)
  object space 21248K, 13% used [0x00000007dae00000,0x00000007db0ce2f0,0x00000007dc2c0000)
```
and
```
Heap after GC invocations=2 (full 1):
 PSYoungGen      total 38208K, used 13540K [0x00000007f5560000, 0x00000007f8000000, 0x0000000800000000)
  eden space 32768K, 41% used [0x00000007f5560000,0x00000007f6299058,0x00000007f7560000)
  from space 5440K, 0% used [0x00000007f7560000,0x00000007f7560000,0x00000007f7ab0000)
  to   space 5440K, 0% used [0x00000007f7ab0000,0x00000007f7ab0000,0x00000007f8000000)
 ParOldGen       total 87424K, used 77375K [0x00000007e0000000, 0x00000007e5560000, 0x00000007f5560000)
  object space 87424K, 88% used [0x00000007e0000000,0x00000007e4b8ffe8,0x00000007e5560000)
 PSPermGen       total 21248K, used 2870K [0x00000007dae00000, 0x00000007dc2c0000, 0x00000007e0000000)
  object space 21248K, 13% used [0x00000007dae00000,0x00000007db0cdaf8,0x00000007dc2c0000)
}

* Concurrent mark and sweep with serial copy collector for new gen
```
{Heap before GC invocations=9 (full 3):
 def new generation   total 76672K, used 60114K [0x0000000105450000, 0x000000010a780000, 0x000000010a780000)
  eden space 68160K,  88% used [0x0000000105450000, 0x0000000108f04a50, 0x00000001096e0000)
  from space 8512K,   0% used [0x00000001096e0000, 0x00000001096e0098, 0x0000000109f30000)
  to   space 8512K,   0% used [0x0000000109f30000, 0x0000000109f30000, 0x000000010a780000)
 concurrent mark-sweep generation total 439104K, used 389637K [0x000000010a780000, 0x0000000125450000, 0x0000000125450000)
 concurrent-mark-sweep perm gen total 21248K, used 2871K [0x0000000125450000, 0x0000000126910000, 0x000000012a650000)
```
and
```
Heap after GC invocations=10 (full 4):
 def new generation   total 76672K, used 13124K [0x0000000105450000, 0x000000010a780000, 0x000000010a780000)
  eden space 68160K,  19% used [0x0000000105450000, 0x0000000106121128, 0x00000001096e0000)
  from space 8512K,   0% used [0x00000001096e0000, 0x00000001096e0000, 0x0000000109f30000)
  to   space 8512K,   0% used [0x0000000109f30000, 0x0000000109f30000, 0x000000010a780000)
 concurrent mark-sweep generation total 439104K, used 435864K [0x000000010a780000, 0x0000000125450000, 0x0000000125450000)
 concurrent-mark-sweep perm gen total 21248K, used 2871K [0x0000000125450000, 0x0000000126910000, 0x000000012a650000)
}
```

* Concurrent mark and sweep with parallel copy collector for new gen
```
{Heap before GC invocations=14 (full 3):
 par new generation   total 153344K, used 130615K [0x00000007dae00000, 0x00000007e5460000, 0x00000007e5460000)
  eden space 136320K,  95% used [0x00000007dae00000, 0x00000007e2d8dde8, 0x00000007e3320000)
  from space 17024K,   0% used [0x00000007e3320000, 0x00000007e3320000, 0x00000007e43c0000)
  to   space 17024K,   0% used [0x00000007e43c0000, 0x00000007e43c0000, 0x00000007e5460000)
 concurrent mark-sweep generation total 353920K, used 339298K [0x00000007e5460000, 0x00000007fae00000, 0x00000007fae00000)
 concurrent-mark-sweep perm gen total 21248K, used 2871K [0x00000007fae00000, 0x00000007fc2c0000, 0x0000000800000000)
```
and
```
Heap after GC invocations=15 (full 4):
 par new generation   total 153344K, used 130615K [0x00000007dae00000, 0x00000007e5460000, 0x00000007e5460000)
  eden space 136320K,  95% used [0x00000007dae00000, 0x00000007e2d8dde8, 0x00000007e3320000)
  from space 17024K,   0% used [0x00000007e3320000, 0x00000007e3320000, 0x00000007e43c0000)
  to   space 17024K,   0% used [0x00000007e43c0000, 0x00000007e43c0000, 0x00000007e5460000)
 concurrent mark-sweep generation total 353920K, used 339298K [0x00000007e5460000, 0x00000007fae00000, 0x00000007fae00000)
 concurrent-mark-sweep perm gen total 21248K, used 2871K [0x00000007fae00000, 0x00000007fc2c0000, 0x0000000800000000)
}
```

* G1
```
{Heap before GC invocations=0 (full 0):
 garbage-first heap   total 131072K, used 40288K [0x00000007dae00000, 0x00000007e2e00000, 0x00000007fae00000)
  region size 1024K, 1 young (1024K), 0 survivors (0K)
 compacting perm gen  total 20480K, used 2864K [0x00000007fae00000, 0x00000007fc200000, 0x0000000800000000)
   the space 20480K,  13% used [0x00000007fae00000, 0x00000007fb0cc080, 0x00000007fb0cc200, 0x00000007fc200000)
No shared spaces configured.
```
and
```
Heap after GC invocations=1 (full 1):
 garbage-first heap   total 131072K, used 40672K [0x00000007dae00000, 0x00000007e2e00000, 0x00000007fae00000)
  region size 1024K, 1 young (1024K), 1 survivors (1024K)
 compacting perm gen  total 20480K, used 2864K [0x00000007fae00000, 0x00000007fc200000, 0x0000000800000000)
   the space 20480K,  13% used [0x00000007fae00000, 0x00000007fb0cc080, 0x00000007fb0cc200, 0x00000007fc200000)
No shared spaces configured.
}
```

# Links

* http://www.oracle.com/technetwork/java/javase/gc-tuning-6-140523.html
* http://www.oracle.com/technetwork/java/example-141412.html
* http://mechanical-sympathy.blogspot.com/2013/07/java-garbage-collection-distilled.html
* http://www.slideshare.net/ludomp/gc-tuning-in-the-hotspot-java-vm-a-fisl-10-presentation
* http://www.oracle.com/in/javaonedevelop/garbage-collection-mythbusters-chuk-400744-en-in.pdf
* https://blog.codecentric.de/en/2012/08/useful-jvm-flags-part-5-young-generation-garbage-collection/
* http://blog.ragozin.info/2011/10/java-cg-hotspots-cms-and-heap.html
* https://blogs.oracle.com/poonam/entry/troubleshooting_long_gc_pauses
* http://www.infoq.com/articles/G1-One-Garbage-Collector-To-Rule-Them-All
* http://middlewaremagic.com/weblogic/?tag=hotspot-jvm
* http://mail.openjdk.java.net/pipermail/hotspot-gc-dev/2012-August/004802.html
* http://blog.griddynamics.com/2011/06/understanding-gc-pauses-in-jvm-hotspots.html
* http://techblog.netflix.com/2013/05/garbage-collection-visualization.html
* http://redstack.wordpress.com/2011/01/06/visualising-garbage-collection-in-the-jvm/
* http://www.cubrid.org/blog/textyle/428187
* https://blogs.oracle.com/jonthecollector/entry/our_collectors#!
* http://portal.acm.org/citation.cfm?id=1029879
