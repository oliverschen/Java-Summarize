#### 第3课

**1 使用 GCLogAnalysis.java 自己演练一遍串行/并行/CMS/G1的案例。**

下面分别使用 2G 和 128 M 堆大小做试验

#### 2G

##### serial GC

```bash
PS F:\chenkui\geek> java -Xms2g -Xmx2g -XX:+UseSerialGC -XX:+PrintGCDetails GCLogAnalysis
正在执行...
[GC (Allocation Failure) [DefNew: 559232K->69888K(629120K), 0.0617403 secs] 559232K->151402K(2027264K), 0.0620671 secs] [Times: user=0.05 sys=0.01, real=0.06 secs]
[GC (Allocation Failure) [DefNew: 629120K->69888K(629120K), 0.0785224 secs] 710634K->274151K(2027264K), 0.0788125 secs] [Times: user=0.02 sys=0.06, real=0.08 secs]
[GC (Allocation Failure) [DefNew: 629120K->69887K(629120K), 0.0565111 secs] 833383K->393987K(2027264K), 0.0567893 secs] [Times: user=0.03 sys=0.03, real=0.06 secs]
[GC (Allocation Failure) [DefNew: 629119K->69887K(629120K), 0.0566070 secs] 953219K->513288K(2027264K), 0.0568572 secs] [Times: user=0.05 sys=0.02, real=0.06 secs]
[GC (Allocation Failure) [DefNew: 629119K->69887K(629120K), 0.0575527 secs] 1072520K->634239K(2027264K), 0.0578656 secs] [Times: user=0.05 sys=0.01, real=0.06 secs]
[GC (Allocation Failure) [DefNew: 629119K->69888K(629120K), 0.0564358 secs] 1193471K->751483K(2027264K), 0.0567928 secs] [Times: user=0.03 sys=0.03, real=0.06 secs]
[GC (Allocation Failure) [DefNew: 629120K->69887K(629120K), 0.0565229 secs] 1310715K->870037K(2027264K), 0.0567883 secs] [Times: user=0.05 sys=0.02, real=0.06 secs]
执行结束!共生成对象次数:14724
Heap
 def new generation   total 629120K, used 92268K [0x0000000080000000, 0x00000000aaaa0000, 0x00000000aaaa0000)
  eden space 559232K,   4% used [0x0000000080000000, 0x00000000815db110, 0x00000000a2220000)
  from space 69888K,  99% used [0x00000000a6660000, 0x00000000aaa9fff0, 0x00000000aaaa0000)
  to   space 69888K,   0% used [0x00000000a2220000, 0x00000000a2220000, 0x00000000a6660000)
 tenured generation   total 1398144K, used 800149K [0x00000000aaaa0000, 0x0000000100000000, 0x0000000100000000)
   the space 1398144K,  57% used [0x00000000aaaa0000, 0x00000000db805530, 0x00000000db805600, 0x0000000100000000)
 Metaspace       used 2684K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 288K, capacity 386K, committed 512K, reserved 1048576K
```

**在 2g 情况下发生 7 次 young GC，共生成对象数 14724**

##### PS GC

```bash
PS F:\chenkui\geek> java -Xms2g -Xmx2g  -XX:+PrintGCDetails GCLogAnalysis
正在执行...
[GC (Allocation Failure) [PSYoungGen: 524800K->87035K(611840K)] 524800K->147373K(2010112K), 0.0354067 secs] [Times: user=0.02 sys=0.16, real=0.04 secs]
[GC (Allocation Failure) [PSYoungGen: 611835K->87023K(611840K)] 672173K->251012K(2010112K), 0.0346053 secs] [Times: user=0.01 sys=0.17, real=0.03 secs]
[GC (Allocation Failure) [PSYoungGen: 611823K->87034K(611840K)] 775812K->357514K(2010112K), 0.0279967 secs] [Times: user=0.09 sys=0.05, real=0.03 secs]
[GC (Allocation Failure) [PSYoungGen: 611834K->87030K(611840K)] 882314K->471488K(2010112K), 0.0291138 secs] [Times: user=0.02 sys=0.17, real=0.03 secs]
[GC (Allocation Failure) [PSYoungGen: 611830K->87039K(611840K)] 996288K->582556K(2010112K), 0.0278855 secs] [Times: user=0.03 sys=0.06, real=0.03 secs]
[GC (Allocation Failure) [PSYoungGen: 611839K->87037K(322048K)] 1107356K->701477K(1720320K), 0.0287161 secs] [Times: user=0.03 sys=0.06, real=0.03 secs]
[GC (Allocation Failure) [PSYoungGen: 322045K->134856K(370176K)] 936485K->755670K(1768448K), 0.0142399 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
[GC (Allocation Failure) [PSYoungGen: 369864K->166997K(465920K)] 990678K->799637K(1864192K), 0.0180826 secs] [Times: user=0.05 sys=0.02, real=0.02 secs]
[GC (Allocation Failure) [PSYoungGen: 399957K->173937K(465920K)] 1032597K->841074K(1864192K), 0.0230406 secs] [Times: user=0.03 sys=0.02, real=0.02 secs]
[GC (Allocation Failure) [PSYoungGen: 406897K->129056K(462336K)] 1074034K->880826K(1860608K), 0.0272829 secs] [Times: user=0.06 sys=0.08, real=0.03 secs]
[GC (Allocation Failure) [PSYoungGen: 362016K->83733K(465920K)] 1113786K->941714K(1864192K), 0.0269741 secs] [Times: user=0.08 sys=0.06, real=0.03 secs]
[GC (Allocation Failure) [PSYoungGen: 316693K->71737K(465920K)] 1174674K->994981K(1864192K), 0.0173698 secs] [Times: user=0.00 sys=0.08, real=0.02 secs]
执行结束!共生成对象次数:17406
Heap
 PSYoungGen      total 465920K, used 123342K [0x00000000d5580000, 0x0000000100000000, 0x0000000100000000)
  eden space 232960K, 22% used [0x00000000d5580000,0x00000000d87e54b8,0x00000000e3900000)
  from space 232960K, 30% used [0x00000000f1c80000,0x00000000f628e570,0x0000000100000000)
  to   space 232960K, 0% used [0x00000000e3900000,0x00000000e3900000,0x00000000f1c80000)
 ParOldGen       total 1398272K, used 923243K [0x0000000080000000, 0x00000000d5580000, 0x00000000d5580000)
  object space 1398272K, 66% used [0x0000000080000000,0x00000000b859aeb8,0x00000000d5580000)
 Metaspace       used 2684K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 288K, capacity 386K, committed 512K, reserved 1048576K
```

**并行 GC 2g 情况下发生 12 次  young GC,共创建 17406个对象**

##### CMS GC

```bash
PS F:\chenkui\geek> java -Xms2g -Xmx2g -XX:+UseConcMarkSweepGC  -XX:+PrintGCDetails GCLogAnalysis
正在执行...
[GC (Allocation Failure) [ParNew: 409024K->51072K(460096K), 0.0169422 secs] 409024K->117791K(2046080K), 0.0172845 secs] [Times: user=0.03 sys=0.06, real=0.02 secs]
[GC (Allocation Failure) [ParNew: 460096K->51072K(460096K), 0.0215917 secs] 526815K->218002K(2046080K), 0.0217754 secs] [Times: user=0.05 sys=0.03, real=0.02 secs]
[GC (Allocation Failure) [ParNew: 460096K->51072K(460096K), 0.0430350 secs] 627026K->316062K(2046080K), 0.0432861 secs] [Times: user=0.28 sys=0.00, real=0.04 secs]
[GC (Allocation Failure) [ParNew: 460096K->51072K(460096K), 0.0463350 secs] 725086K->426459K(2046080K), 0.0465504 secs] [Times: user=0.24 sys=0.03, real=0.05 secs]
[GC (Allocation Failure) [ParNew: 460096K->51072K(460096K), 0.0478689 secs] 835483K->526402K(2046080K), 0.0480074 secs] [Times: user=0.16 sys=0.03, real=0.05 secs]
[GC (Allocation Failure) [ParNew: 460096K->51071K(460096K), 0.0510291 secs] 935426K->633552K(2046080K), 0.0512245 secs] [Times: user=0.20 sys=0.05, real=0.05 secs]
[GC (Allocation Failure) [ParNew: 460095K->51072K(460096K), 0.0443577 secs] 1042576K->736857K(2046080K), 0.0444812 secs] [Times: user=0.26 sys=0.02, real=0.04 secs]
[GC (Allocation Failure) [ParNew: 460096K->51072K(460096K), 0.0441698 secs] 1145881K->840689K(2046080K), 0.0442948 secs] [Times: user=0.23 sys=0.02, real=0.04 secs]
[GC (Allocation Failure) [ParNew: 460096K->51072K(460096K), 0.0468603 secs] 1249713K->947944K(2046080K), 0.0470125 secs] [Times: user=0.13 sys=0.05, real=0.05 secs]
[GC (CMS Initial Mark) [1 CMS-initial-mark: 896872K(1585984K)] 956112K(2046080K), 0.0001955 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[CMS-concurrent-mark-start]
[CMS-concurrent-mark: 0.005/0.005 secs] [Times: user=0.03 sys=0.02, real=0.00 secs]
[CMS-concurrent-preclean-start]
[CMS-concurrent-preclean: 0.001/0.001 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[CMS-concurrent-abortable-preclean-start]
[GC (Allocation Failure) [ParNew[CMS-concurrent-abortable-preclean: 0.001/0.101 secs] [Times: user=0.30 sys=0.06, real=0.10 secs]
: 460096K->51072K(460096K), 0.0650852 secs] 1356968K->1049001K(2046080K), 0.0652254 secs] [Times: user=0.28 sys=0.06, real=0.06 secs]
[GC (CMS Final Remark) [YG occupancy: 51498 K (460096 K)][Rescan (parallel) , 0.0006473 secs][weak refs processing, 0.0000310 secs][class unloading, 0.0003260 secs][scrub symbol table, 0.0003267 secs][scrub string table, 0.0001353 secs][1 CMS-remark: 997929K(1585984K)] 1049428K(2046080K), 0.0017756 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[CMS-concurrent-sweep-start]
执行结束!共生成对象次数:15602
Heap
 par new generation   total 460096K, used 67783K [0x0000000080000000, 0x000000009f330000, 0x000000009f330000)
  eden space 409024K,   4% used [0x0000000080000000, 0x0000000081051f20, 0x0000000098f70000)
  from space 51072K, 100% used [0x0000000098f70000, 0x000000009c150000, 0x000000009c150000)
  to   space 51072K,   0% used [0x000000009c150000, 0x000000009c150000, 0x000000009f330000)
 concurrent mark-sweep generation total 1585984K, used 578406K [0x000000009f330000, 0x0000000100000000, 0x0000000100000000)
 Metaspace       used 2684K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 288K, capacity 386K, committed 512K, reserved 1048576K
```

**cms GC 2g 情况下发生 9 次 young GC，创建 15602 个对象。**

##### G1

```
PS F:\chenkui\geek> java -Xms2g -Xmx2g -XX:+UseG1GC  -XX:+PrintGC GCLogAnalysis
正在执行...
[GC pause (G1 Evacuation Pause) (young) 123M->45M(2048M), 0.0054700 secs]
[GC pause (G1 Evacuation Pause) (young) 152M->79M(2048M), 0.0060399 secs]
[GC pause (G1 Evacuation Pause) (young) 190M->116M(2048M), 0.0050988 secs]
[GC pause (G1 Evacuation Pause) (young) 227M->154M(2048M), 0.0052115 secs]
[GC pause (G1 Evacuation Pause) (young) 268M->184M(2048M), 0.0052244 secs]
[GC pause (G1 Evacuation Pause) (young) 333M->235M(2048M), 0.0068830 secs]
[GC pause (G1 Evacuation Pause) (young)-- 1772M->1048M(2048M), 0.0521552 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 1049M->1048M(2048M), 0.0184864 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0001636 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0383017 secs]
[GC remark, 0.0038136 secs]
[GC cleanup 1252M->1208M(2048M), 0.0019333 secs]
[GC concurrent-cleanup-start]
[GC concurrent-cleanup-end, 0.0004621 secs]
执行结束!共生成对象次数:9379
```

**g1 GC 在 2g 情况下共发生 6 次 young GC，创建 9379 个对象**

#### 128M

##### Serial GC

```bash
PS F:\chenkui\geek> java -Xms128m -Xmx128m -XX:+UseSerialGC -XX:+PrintGCDetails GCLogAnalysis
正在执行...
[GC (Allocation Failure) [DefNew: 34868K->4351K(39296K), 0.0063928 secs] 34868K->13530K(126720K), 0.0067176 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
[GC (Allocation Failure) [DefNew: 39295K->4351K(39296K), 0.0066988 secs] 48474K->24187K(126720K), 0.0069468 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
[GC (Allocation Failure) [DefNew: 39295K->4348K(39296K), 0.0071733 secs] 59131K->39684K(126720K), 0.0074686 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
[GC (Allocation Failure) [DefNew: 39141K->4344K(39296K), 0.0058181 secs] 74476K->51846K(126720K), 0.0061334 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
[GC (Allocation Failure) [DefNew: 38962K->4348K(39296K), 0.0062850 secs] 86465K->64357K(126720K), 0.0066479 secs] [Times: user=0.00 sys=0.02, real=0.01 secs]
[GC (Allocation Failure) [DefNew: 39292K->4350K(39296K), 0.0056429 secs] 99301K->76276K(126720K), 0.0059455 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
[GC (Allocation Failure) [DefNew: 39287K->4351K(39296K), 0.0058783 secs] 111213K->88323K(126720K), 0.0061795 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
[GC (Allocation Failure) [DefNew: 39295K->39295K(39296K), 0.0002508 secs][Tenured: 83972K->87041K(87424K), 0.0257375 secs] 123267K->92045K(126720K), [Metaspace: 2677K->2677K(1056768K)], 0.0265846 secs] [Times: user=0.01 sys=0.00, real=0.03 secs]
[Full GC (Allocation Failure) [Tenured: 87041K->87241K(87424K), 0.0109399 secs] 126189K->100867K(126720K), [Metaspace: 2677K->2677K(1056768K)], 0.0114500 secs] [Times: user=0.02 sys=0.00, real=0.01 secs]
[Full GC (Allocation Failure) [Tenured: 87241K->87185K(87424K), 0.0105836 secs] 126330K->107215K(126720K), [Metaspace: 2677K->2677K(1056768K)], 0.0111391 secs] [Times: user=0.01 sys=0.00, real=0.01 secs]
[Full GC (Allocation Failure) [Tenured: 87185K->87416K(87424K), 0.0146376 secs] 126286K->107905K(126720K), [Metaspace: 2677K->2677K(1056768K)], 0.0151471 secs] [Times: user=0.02 sys=0.00, real=0.01 secs]
[Full GC (Allocation Failure) [Tenured: 87416K->87416K(87424K), 0.0051434 secs] 126603K->114396K(126720K), [Metaspace: 2677K->2677K(1056768K)], 0.0056545 secs] [Times: user=0.02 sys=0.00, real=0.01 secs]
[Full GC (Allocation Failure) [Tenured: 87416K->87416K(87424K), 0.0055707 secs] 126650K->120022K(126720K), [Metaspace: 2677K->2677K(1056768K)], 0.0060131 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
[Full GC (Allocation Failure) [Tenured: 87416K->87416K(87424K), 0.0045498 secs] 126642K->122830K(126720K), [Metaspace: 2677K->2677K(1056768K)], 0.0047956 secs] [Times: user=0.02 sys=0.00, real=0.00 secs]
[Full GC (Allocation Failure) [Tenured: 87416K->87152K(87424K), 0.0148659 secs] 126206K->120539K(126720K), [Metaspace: 2677K->2677K(1056768K)], 0.0153520 secs] [Times: user=0.02 sys=0.00, real=0.02 secs]
[Full GC (Allocation Failure) [Tenured: 87152K->87152K(87424K), 0.0018177 secs] 125934K->122220K(126720K), [Metaspace: 2677K->2677K(1056768K)], 0.0021397 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[Full GC (Allocation Failure) [Tenured: 87152K->87152K(87424K), 0.0012953 secs] 125843K->123397K(126720K), [Metaspace: 2677K->2677K(1056768K)], 0.0014917 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[Full GC (Allocation Failure) [Tenured: 87421K->87421K(87424K), 0.0011755 secs] 126707K->124759K(126720K), [Metaspace: 2677K->2677K(1056768K)], 0.0013358 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[Full GC (Allocation Failure) [Tenured: 87421K->87335K(87424K), 0.0114545 secs] 126345K->124190K(126720K), [Metaspace: 2677K->2677K(1056768K)], 0.0119233 secs] [Times: user=0.02 sys=0.00, real=0.01 secs]
[Full GC (Allocation Failure) [Tenured: 87335K->87335K(87424K), 0.0014771 secs] 126012K->124388K(126720K), [Metaspace: 2677K->2677K(1056768K)], 0.0019095 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[Full GC (Allocation Failure) [Tenured: 87335K->87335K(87424K), 0.0010538 secs] 126039K->125048K(126720K), [Metaspace: 2677K->2677K(1056768K)], 0.0012383 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[Full GC (Allocation Failure) [Tenured: 87335K->87335K(87424K), 0.0014363 secs] 125706K->125048K(126720K), [Metaspace: 2677K->2677K(1056768K)], 0.0016556 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[Full GC (Allocation Failure) [Tenured: 87335K->87335K(87424K), 0.0013113 secs] 126530K->125293K(126720K), [Metaspace: 2677K->2677K(1056768K)], 0.0015931 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[Full GC (Allocation Failure) [Tenured: 87335K->87335K(87424K), 0.0015109 secs] 126114K->125253K(126720K), [Metaspace: 2677K->2677K(1056768K)], 0.0017334 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
[Full GC (Allocation Failure) [Tenured: 87407K->87407K(87424K), 0.0012223 secs] 126663K->126296K(126720K), [Metaspace: 2677K->2677K(1056768K)], 0.0014571 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[Full GC (Allocation Failure) [Tenured: 87407K->87407K(87424K), 0.0010881 secs] 126646K->126296K(126720K), [Metaspace: 2677K->2677K(1056768K)], 0.0012479 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[Full GC (Allocation Failure) [Tenured: 87407K->87315K(87424K), 0.0096136 secs] 126296K->126205K(126720K), [Metaspace: 2677K->2677K(1056768K)], 0.0101547 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
        at GCLogAnalysis.generateGarbage(GCLogAnalysis.java:42)
        at GCLogAnalysis.main(GCLogAnalysis.java:25)
Heap
 def new generation   total 39296K, used 39223K [0x00000000f8000000, 0x00000000faaa0000, 0x00000000faaa0000)
  eden space 34944K, 100% used [0x00000000f8000000, 0x00000000fa220000, 0x00000000fa220000)
  from space 4352K,  98% used [0x00000000fa660000, 0x00000000faa8dd48, 0x00000000faaa0000)
  to   space 4352K,   0% used [0x00000000fa220000, 0x00000000fa220000, 0x00000000fa660000)
 tenured generation   total 87424K, used 87315K [0x00000000faaa0000, 0x0000000100000000, 0x0000000100000000)
   the space 87424K,  99% used [0x00000000faaa0000, 0x00000000fffe4f98, 0x00000000fffe5000, 0x0000000100000000)
 Metaspace       used 2708K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 291K, capacity 386K, committed 512K, reserved 1048576K
```

**在 128M 内存下，串行 GC 发生 8 次 young GC 后又发生了 20 次 full GC 发生 OOM:Java heap space**

##### PS GC

```bash
PS F:\chenkui\geek> java -Xms128m -Xmx128m  -XX:+PrintGCDetails GCLogAnalysis
正在执行...
[GC (Allocation Failure) [PSYoungGen: 33252K->5118K(38400K)] 33252K->12112K(125952K), 0.0026090 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[GC (Allocation Failure) [PSYoungGen: 38150K->5118K(38400K)] 45144K->23208K(125952K), 0.0028288 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[GC (Allocation Failure) [PSYoungGen: 38398K->5116K(38400K)] 56488K->34511K(125952K), 0.0028670 secs] [Times: user=0.00 sys=0.08, real=0.00 secs]
[GC (Allocation Failure) [PSYoungGen: 38396K->5105K(38400K)] 67791K->44287K(125952K), 0.0030824 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[GC (Allocation Failure) [PSYoungGen: 38227K->5116K(38400K)] 77409K->55727K(125952K), 0.0032612 secs] [Times: user=0.02 sys=0.05, real=0.00 secs]
[GC (Allocation Failure) [PSYoungGen: 38063K->5109K(19968K)] 88674K->66088K(107520K), 0.0026595 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[GC (Allocation Failure) [PSYoungGen: 19831K->7719K(29184K)] 80810K->70179K(116736K), 0.0017161 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[GC (Allocation Failure) [PSYoungGen: 22440K->11520K(29184K)] 84901K->75308K(116736K), 0.0022777 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[GC (Allocation Failure) [PSYoungGen: 26368K->14099K(29184K)] 90156K->79573K(116736K), 0.0021809 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[GC (Allocation Failure) [PSYoungGen: 28746K->10862K(29184K)] 94220K->85372K(116736K), 0.0030364 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[Full GC (Ergonomics) [PSYoungGen: 10862K->0K(29184K)] [ParOldGen: 74509K->79503K(87552K)] 85372K->79503K(116736K), [Metaspace: 2677K->2677K(1056768K)], 0.0111121 secs] [Times: user=0.06 sys=0.00, real=0.01 secs]
[Full GC (Ergonomics) [PSYoungGen: 14734K->0K(29184K)] [ParOldGen: 79503K->80763K(87552K)] 94238K->80763K(116736K), [Metaspace: 2677K->2677K(1056768K)], 0.0112544 secs] [Times: user=0.09 sys=0.00, real=0.01 secs]
[Full GC (Ergonomics) [PSYoungGen: 14306K->0K(29184K)] [ParOldGen: 80763K->83404K(87552K)] 95069K->83404K(116736K), [Metaspace: 2677K->2677K(1056768K)], 0.0102159 secs] [Times: user=0.06 sys=0.00, real=0.01 secs]
[Full GC (Ergonomics) [PSYoungGen: 14687K->0K(29184K)] [ParOldGen: 83404K->86951K(87552K)] 98091K->86951K(116736K), [Metaspace: 2677K->2677K(1056768K)], 0.0093051 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
[Full GC (Ergonomics) [PSYoungGen: 14848K->1988K(29184K)] [ParOldGen: 86951K->87281K(87552K)] 101799K->89270K(116736K), [Metaspace: 2677K->2677K(1056768K)], 0.0064519 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
[Full GC (Ergonomics) [PSYoungGen: 14831K->6209K(29184K)] [ParOldGen: 87281K->87034K(87552K)] 102113K->93244K(116736K), [Metaspace: 2677K->2677K(1056768K)], 0.0110523 secs] [Times: user=0.08 sys=0.00, real=0.01 secs]
[Full GC (Ergonomics) [PSYoungGen: 14773K->9513K(29184K)] [ParOldGen: 87034K->87131K(87552K)] 101808K->96645K(116736K), [Metaspace: 2677K->2677K(1056768K)], 0.0091157 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
[Full GC (Ergonomics) [PSYoungGen: 14732K->10983K(29184K)] [ParOldGen: 87131K->87127K(87552K)] 101864K->98110K(116736K), [Metaspace: 2677K->2677K(1056768K)], 0.0091381 secs] [Times: user=0.02 sys=0.00, real=0.01 secs]
[Full GC (Ergonomics) [PSYoungGen: 14497K->12608K(29184K)] [ParOldGen: 87127K->87038K(87552K)] 101625K->99646K(116736K), [Metaspace: 2677K->2677K(1056768K)], 0.0084782 secs] [Times: user=0.08 sys=0.00, real=0.01 secs]
[Full GC (Ergonomics) [PSYoungGen: 14800K->12991K(29184K)] [ParOldGen: 87038K->86666K(87552K)] 101839K->99658K(116736K), [Metaspace: 2677K->2677K(1056768K)], 0.0069184 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
[Full GC (Ergonomics) [PSYoungGen: 14357K->12505K(29184K)] [ParOldGen: 86666K->87545K(87552K)] 101024K->100050K(116736K), [Metaspace: 2677K->2677K(1056768K)], 0.0042810 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[Full GC (Ergonomics) [PSYoungGen: 14612K->13702K(29184K)] [ParOldGen: 87545K->87545K(87552K)] 102157K->101247K(116736K), [Metaspace: 2677K->2677K(1056768K)], 0.0019841 secs] [Times: user=0.02 sys=0.00, real=0.00 secs]
[Full GC (Ergonomics) [PSYoungGen: 14813K->14450K(29184K)] [ParOldGen: 87545K->87545K(87552K)] 102358K->101996K(116736K), [Metaspace: 2677K->2677K(1056768K)], 0.0017557 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[Full GC (Ergonomics) [PSYoungGen: 14693K->14193K(29184K)] [ParOldGen: 87545K->87545K(87552K)] 102238K->101739K(116736K), [Metaspace: 2677K->2677K(1056768K)], 0.0015282 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[Full GC (Ergonomics) [PSYoungGen: 14656K->14371K(29184K)] [ParOldGen: 87545K->87545K(87552K)] 102202K->101917K(116736K), [Metaspace: 2677K->2677K(1056768K)], 0.0017629 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[Full GC (Ergonomics) [PSYoungGen: 14834K->14752K(29184K)] [ParOldGen: 87545K->87545K(87552K)] 102379K->102298K(116736K), [Metaspace: 2677K->2677K(1056768K)], 0.0021312 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[Full GC (Allocation Failure) [PSYoungGen: 14752K->14752K(29184K)] [ParOldGen: 87545K->87526K(87552K)] 102298K->102278K(116736K), [Metaspace: 2677K->2677K(1056768K)], 0.0115029 secs] [Times: user=0.08 sys=0.00, real=0.01 secs]
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
        at GCLogAnalysis.generateGarbage(GCLogAnalysis.java:42)
        at GCLogAnalysis.main(GCLogAnalysis.java:25)
Heap
 PSYoungGen      total 29184K, used 14848K [0x00000000fd580000, 0x0000000100000000, 0x0000000100000000)
  eden space 14848K, 100% used [0x00000000fd580000,0x00000000fe400000,0x00000000fe400000)
  from space 14336K, 0% used [0x00000000ff200000,0x00000000ff200000,0x0000000100000000)
  to   space 14336K, 0% used [0x00000000fe400000,0x00000000fe400000,0x00000000ff200000)
 ParOldGen       total 87552K, used 87526K [0x00000000f8000000, 0x00000000fd580000, 0x00000000fd580000)
  object space 87552K, 99% used [0x00000000f8000000,0x00000000fd579980,0x00000000fd580000)
 Metaspace       used 2708K, capacity 4486K, committed 4864K, reserved 1056768K
  class space    used 291K, capacity 386K, committed 512K, reserved 1048576K
```

**128M 内存下，并行 GC 发生 10 次 young GC 后又发生了 17 次 full GC 后 OOM:Java heap space**

##### CMS GC

```bash
PS F:\chenkui\geek> java -Xms128m -Xmx128m -XX:+UseConcMarkSweepGC -XX:+PrintGC GCLogAnalysis
正在执行...
[GC (Allocation Failure)  34385K->12092K(126720K), 0.0023795 secs]
[GC (Allocation Failure)  47006K->25645K(126720K), 0.0038899 secs]
[GC (Allocation Failure)  60409K->36636K(126720K), 0.0048914 secs]
[GC (Allocation Failure)  71494K->48018K(126720K), 0.0049146 secs]
[GC (Allocation Failure)  82438K->63292K(126720K), 0.0065276 secs]
[GC (CMS Initial Mark)  65343K(126720K), 0.0005983 secs]
[GC (Allocation Failure)  98091K->73616K(126720K), 0.0046417 secs]
[GC (Allocation Failure)  108042K->84079K(126720K), 0.0044963 secs]
[Full GC (Allocation Failure)  119023K->87897K(126720K), 0.0133060 secs]
[Full GC (Allocation Failure)  126527K->95732K(126720K), 0.0119262 secs]
[GC (CMS Initial Mark)  95876K(126720K), 0.0002448 secs]
[GC (CMS Final Remark)  106426K(126720K), 0.0007432 secs]
[Full GC (Allocation Failure)  126462K->101447K(126720K), 0.0119377 secs]
[GC (CMS Initial Mark)  102680K(126720K), 0.0001555 secs]
[GC (CMS Final Remark)  113072K(126720K), 0.0008123 secs]
[Full GC (Allocation Failure)  126390K->106427K(126720K), 0.0121916 secs]
[GC (CMS Initial Mark)  107499K(126720K), 0.0002744 secs]
[GC (CMS Final Remark)  118697K(126720K), 0.0007686 secs]
[Full GC (Allocation Failure)  126600K->112465K(126720K), 0.0136468 secs]
[GC (CMS Initial Mark)  112534K(126720K), 0.0001770 secs]
[GC (CMS Final Remark)  124163K(126720K), 0.0007867 secs]
[Full GC (Allocation Failure)  126570K->116480K(126720K), 0.0128598 secs]
[GC (CMS Initial Mark)  116666K(126720K), 0.0002090 secs]
[Full GC (Allocation Failure)  126587K->119666K(126720K), 0.0127844 secs]
[Full GC (Allocation Failure)  126425K->120810K(126720K), 0.0131562 secs]
[GC (CMS Initial Mark)  121008K(126720K), 0.0001697 secs]
[Full GC (Allocation Failure)  126290K->121889K(126720K), 0.0140632 secs]
[Full GC (Allocation Failure)  125587K->123260K(126720K), 0.0131173 secs]
[GC (CMS Initial Mark)  123942K(126720K), 0.0002586 secs]
[Full GC (Allocation Failure)  126432K->124880K(126720K), 0.0016012 secs]
[Full GC (Allocation Failure)  126451K->125218K(126720K), 0.0017837 secs]
[GC (CMS Initial Mark)  125385K(126720K), 0.0002765 secs]
[Full GC (Allocation Failure)  125858K->125515K(126720K), 0.0012977 secs]
[Full GC (Allocation Failure)  126239K->126239K(126720K), 0.0012442 secs]
[Full GC (Allocation Failure)  126239K->126220K(126720K), 0.0092375 secs]
[GC (CMS Initial Mark)  126220K(126720K), 0.0001776 secs]
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
[GC (CMS Final Remark)  126243K(126720K), 0.0007618 secs]
        at GCLogAnalysis.generateGarbage(GCLogAnalysis.java:42)
        at GCLogAnalysis.main(GCLogAnalysis.java:25)
```

**在 128M 内存下 7 次 young GC 后又发生 14 full GC 出现 OOM:Java heap space**

##### G1

```bash
PS F:\chenkui\geek> java -Xms128m -Xmx128m -XX:+UseG1GC -XX:+PrintGC GCLogAnalysis
正在执行...
[GC pause (G1 Evacuation Pause) (young) 11M->4463K(128M), 0.0202706 secs]
[GC pause (G1 Evacuation Pause) (young) 28M->11M(128M), 0.0033889 secs]
[GC pause (G1 Evacuation Pause) (young)-- 100M->76M(128M), 0.0031587 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 79M->77M(128M), 0.0015882 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0002444 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0010288 secs]
[GC remark, 0.0007047 secs]
[GC cleanup 87M->87M(128M), 0.0007763 secs]
[GC pause (G1 Evacuation Pause) (young) 105M->89M(128M), 0.0015951 secs]
[GC pause (G1 Evacuation Pause) (mixed) 93M->78M(128M), 0.0013094 secs]
[GC pause (G1 Evacuation Pause) (mixed) 84M->70M(128M), 0.0012994 secs]
[GC pause (G1 Evacuation Pause) (mixed) 75M->64M(128M), 0.0009697 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 66M->64M(128M), 0.0005584 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0001523 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0007882 secs]
[GC remark, 0.0007811 secs]
[GC cleanup 75M->75M(128M), 0.0002832 secs]
[GC pause (G1 Evacuation Pause) (young)-- 106M->82M(128M), 0.0014316 secs]
[GC pause (G1 Evacuation Pause) (mixed) 88M->81M(128M), 0.0012922 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 83M->82M(128M), 0.0007101 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0008921 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0008750 secs]
[GC remark, 0.0007965 secs]
[GC cleanup 95M->95M(128M), 0.0003116 secs]
[GC pause (G1 Evacuation Pause) (young) 99M->87M(128M), 0.0010008 secs]
[GC pause (G1 Evacuation Pause) (mixed) 92M->82M(128M), 0.0011981 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 84M->83M(128M), 0.0006132 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0001471 secs]
[GC concurrent-mark-start]
[GC concurrent-mark-end, 0.0007539 secs]
[GC remark, 0.0007630 secs]
[GC cleanup 92M->92M(128M), 0.0002571 secs]
[GC pause (G1 Evacuation Pause) (young) 99M->89M(128M), 0.0009918 secs]
[GC pause (G1 Evacuation Pause) (mixed) 95M->91M(128M), 0.0008574 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 96M->92M(128M), 0.0005701 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0001310 secs]
[GC concurrent-mark-start]
[GC pause (G1 Evacuation Pause) (young) 99M->94M(128M), 0.0007446 secs]
[GC concurrent-mark-end, 0.0017162 secs]
[GC remark, 0.0008358 secs]
[GC cleanup 96M->96M(128M), 0.0002735 secs]
[GC pause (G1 Evacuation Pause) (young) 101M->97M(128M), 0.0007914 secs]
[GC pause (G1 Evacuation Pause) (mixed) 104M->98M(128M), 0.0009091 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 98M->98M(128M), 0.0006063 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0001105 secs]
[GC concurrent-mark-start]
[GC pause (G1 Evacuation Pause) (young)[GC concurrent-mark-end, 0.0011410 secs]
 105M->101M(128M), 0.0008772 secs]
[GC remark, 0.0009680 secs]
[GC cleanup 102M->102M(128M), 0.0002856 secs]
[GC pause (G1 Evacuation Pause) (young) 107M->104M(128M), 0.0007690 secs]
[GC pause (G1 Evacuation Pause) (mixed)-- 109M->109M(128M), 0.0010339 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 110M->109M(128M), 0.0005060 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0001226 secs]
[GC concurrent-mark-start]
[GC pause (G1 Evacuation Pause) (young)-- 111M->111M(128M), 0.0004541 secs]
[GC pause (G1 Evacuation Pause) (young)-- 112M->112M(128M), 0.0004300 secs]
[Full GC (Allocation Failure)  112M->94M(128M), 0.0117964 secs]
[GC concurrent-mark-abort]
[GC pause (G1 Evacuation Pause) (young) 100M->96M(128M), 0.0006463 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 99M->97M(128M), 0.0004647 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0001050 secs]
[GC concurrent-mark-start]
[GC pause (G1 Humongous Allocation) (young)-- 103M->101M(128M), 0.0006632 secs]
[GC concurrent-mark-end, 0.0016008 secs]
[GC pause (G1 Evacuation Pause) (young)-- 103M->103M(128M), 0.0004719 secs]
[GC remark, 0.0007467 secs]
[GC cleanup 103M->103M(128M), 0.0005208 secs]
[GC pause (G1 Humongous Allocation) (young)-- 104M->104M(128M), 0.0006208 secs]
[Full GC (Allocation Failure)  104M->96M(128M), 0.0070535 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 99M->97M(128M), 0.0004961 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0001441 secs]
[GC concurrent-mark-start]
[GC pause (G1 Evacuation Pause) (young)-- 101M->101M(128M), 0.0006119 secs]
[GC pause (G1 Evacuation Pause) (young)-- 102M->102M(128M), 0.0004378 secs]
[Full GC (Allocation Failure)  102M->98M(128M), 0.0038319 secs]
[GC concurrent-mark-abort]
[GC pause (G1 Humongous Allocation) (young) (initial-mark) 100M->99M(128M), 0.0004482 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0000835 secs]
[GC concurrent-mark-start]
[GC pause (G1 Humongous Allocation) (young)-- 100M->99M(128M), 0.0006276 secs]
[GC pause (G1 Evacuation Pause) (young)-- 100M->100M(128M), 0.0005702 secs]
[GC pause (G1 Humongous Allocation) (young)-- 101M->101M(128M), 0.0004988 secs]
[GC concurrent-mark-end, 0.0031338 secs]
[Full GC (Allocation Failure)  101M->99M(128M), 0.0078147 secs]
[GC remark, 0.0000291 secs]
[GC concurrent-mark-abort]
[GC pause (G1 Evacuation Pause) (young)-- 102M->102M(128M), 0.0004978 secs]
[Full GC (Allocation Failure)  102M->100M(128M), 0.0016831 secs]
[GC pause (G1 Humongous Allocation) (young) (initial-mark)-- 102M->101M(128M), 0.0004723 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0000596 secs]
[GC concurrent-mark-start]
[GC pause (G1 Evacuation Pause) (young) 101M->101M(128M), 0.0005516 secs]
[GC pause (G1 Evacuation Pause) (young)-- 102M->102M(128M), 0.0004274 secs]
[Full GC (Allocation Failure)  102M->100M(128M), 0.0031680 secs]
[GC concurrent-mark-abort]
[GC pause (G1 Humongous Allocation) (young) (initial-mark)-- 101M->101M(128M), 0.0005371 secs]
[GC concurrent-root-region-scan-start]
[GC pause (G1 Humongous Allocation) (young)[GC concurrent-root-region-scan-end, 0.0000712 secs]
[GC concurrent-mark-start]
 101M->101M(128M), 0.0005082 secs]
[Full GC (Allocation Failure)  101M->100M(128M), 0.0104998 secs]
[GC concurrent-mark-abort]
[GC pause (G1 Evacuation Pause) (young)-- 101M->101M(128M), 0.0005174 secs]
[Full GC (Allocation Failure)  101M->101M(128M), 0.0022705 secs]
[Full GC (Allocation Failure)  101M->101M(128M), 0.0047430 secs]
[GC pause (G1 Evacuation Pause) (young) 101M->101M(128M), 0.0006931 secs]
[GC pause (G1 Evacuation Pause) (young) (initial-mark) 101M->101M(128M), 0.0005278 secs]
[GC concurrent-root-region-scan-start]
[GC concurrent-root-region-scan-end, 0.0001125 secs]
[GC concurrent-mark-start]
[Full GC (Allocation Failure)  101M->617K(128M), 0.0024231 secs]
[GC concurrent-mark-abort]
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
        at java.util.Arrays.copyOfRange(Arrays.java:3664)
        at java.lang.String.<init>(String.java:207)
        at java.lang.StringBuilder.toString(StringBuilder.java:407)
        at GCLogAnalysis.generateGarbage(GCLogAnalysis.java:58)
        at GCLogAnalysis.main(GCLogAnalysis.java:25)
```

**内存 128M 在 G1 GC 多次 young GC和 full GC 交替之后发生 OOM**

**2 使用压测工具(wrk或sb)，演练gateway-server-0.0.1-SNAPSHOT.jar 示例。**

物理机配置： i5-8400 16g 内存 win10 

##### 默认 JVM 配置压测

```bash
PS D:\software\sb> .\sb.exe -u http://localhost:8088/api/hello -c 20 -N 60
Starting at 2020/10/28 22:53:08
[Press C to stop the test]
262265  (RPS: 4092)
---------------Finished!----------------
Finished at 2020/10/28 22:54:12 (took 00:01:04.2284617)
Status 200:    262266

RPS: 4290.6 (requests/second)
Max: 473ms
Min: 0ms
Avg: 0.3ms

  50%   below 0ms
  60%   below 0ms
  70%   below 0ms
  80%   below 0ms
  90%   below 0ms
  95%   below 2ms
  98%   below 4ms
  99%   below 6ms
99.9%   below 13ms
```

##### 512M

###### Serial GC

```bash
PS D:\software\sb> .\sb.exe -u http://localhost:8088/api/hello -c 20 -N 60
Starting at 2020/10/28 23:06:43
[Press C to stop the test]
271646  (RPS: 4246.7)
---------------Finished!----------------
Finished at 2020/10/28 23:07:47 (took 00:01:04.1187602)
Status 200:    271650

RPS: 4441.6 (requests/second)
Max: 159ms
Min: 0ms
Avg: 0.3ms

  50%   below 0ms
  60%   below 0ms
  70%   below 0ms
  80%   below 0ms
  90%   below 0ms
  95%   below 2ms
  98%   below 4ms
  99%   below 6ms
99.9%   below 12ms
```

###### CMS

```bash
PS D:\software\sb> .\sb.exe -u http://localhost:8088/api/hello -c 20 -N 60
Starting at 2020/10/28 23:09:06
[Press C to stop the test]
252476  (RPS: 3946.8)
---------------Finished!----------------
Finished at 2020/10/28 23:10:10 (took 00:01:04.1212636)
Status 200:    252477

RPS: 4128.4 (requests/second)
Max: 153ms
Min: 0ms
Avg: 0.3ms

  50%   below 0ms
  60%   below 0ms
  70%   below 0ms
  80%   below 0ms
  90%   below 1ms
  95%   below 2ms
  98%   below 4ms
  99%   below 6ms
99.9%   below 13ms
```

###### PS GC

```bash
PS D:\software\sb> .\sb.exe -u http://localhost:8088/api/hello -c 20 -N 60
Starting at 2020/10/28 23:03:52
[Press C to stop the test]
230397  (RPS: 3603.8)
---------------Finished!----------------
Finished at 2020/10/28 23:04:57 (took 00:01:04.1224226)
Status 200:    230404

RPS: 3765.9 (requests/second)
Max: 166ms
Min: 0ms
Avg: 0.4ms

  50%   below 0ms
  60%   below 0ms
  70%   below 0ms
  80%   below 0ms
  90%   below 1ms
  95%   below 3ms
  98%   below 6ms
  99%   below 8ms
99.9%   below 16ms
```

###### G1

```bash
PS D:\software\sb> .\sb.exe -u http://localhost:8088/api/hello -c 20 -N 60
Starting at 2020/10/28 23:11:41
[Press C to stop the test]
238586  (RPS: 3722.9)
---------------Finished!----------------
Finished at 2020/10/28 23:12:45 (took 00:01:04.2574104)
Status 200:    238586

RPS: 3900.5 (requests/second)
Max: 247ms
Min: 0ms
Avg: 0.4ms

  50%   below 0ms
  60%   below 0ms
  70%   below 0ms
  80%   below 0ms
  90%   below 1ms
  95%   below 3ms
  98%   below 5ms
  99%   below 7ms
99.9%   below 15ms
```

##### 4g

###### Serial GC

```bash
PS D:\software\sb> .\sb.exe -u http://localhost:8088/api/hello -c 20 -N 60
Starting at 2020/10/28 23:16:05
[Press C to stop the test]
183893  (RPS: 2872.9)
---------------Finished!----------------
Finished at 2020/10/28 23:17:09 (took 00:01:04.1931611)
Status 200:    183896

RPS: 3006.7 (requests/second)
Max: 170ms
Min: 0ms
Avg: 0.6ms

  50%   below 0ms
  60%   below 0ms
  70%   below 0ms
  80%   below 0ms
  90%   below 1ms
  95%   below 3ms
  98%   below 7ms
  99%   below 10ms
99.9%   below 29ms
```

###### CMS

```bash
PS D:\software\sb> .\sb.exe -u http://localhost:8088/api/hello -c 20 -N 60
Starting at 2020/10/28 23:28:19
[Press C to stop the test]
251303  (RPS: 3927)
---------------Finished!----------------
Finished at 2020/10/28 23:29:23 (took 00:01:04.1532343)
Status 200:    251310

RPS: 4109.5 (requests/second)
Max: 145ms
Min: 0ms
Avg: 0.3ms

  50%   below 0ms
  60%   below 0ms
  70%   below 0ms
  80%   below 0ms
  90%   below 1ms
  95%   below 2ms
  98%   below 4ms
  99%   below 6ms
99.9%   below 14ms
```

###### PS

```bash
PS D:\software\sb> .\sb.exe -u http://localhost:8088/api/hello -c 20 -N 60
Starting at 2020/10/28 23:30:07
[Press C to stop the test]
220787  (RPS: 3446.1)
---------------Finished!----------------
Finished at 2020/10/28 23:31:11 (took 00:01:04.1022370)
Status 200:    220789

RPS: 3617.9 (requests/second)
Max: 194ms
Min: 0ms
Avg: 0.5ms

  50%   below 0ms
  60%   below 0ms
  70%   below 0ms
  80%   below 0ms
  90%   below 1ms
  95%   below 3ms
  98%   below 6ms
  99%   below 8ms
99.9%   below 17ms
```

###### G1

```bash
PS D:\software\sb> .\sb.exe -u http://localhost:8088/api/hello -c 20 -N 60
Starting at 2020/10/28 23:31:59
[Press C to stop the test]
213024  (RPS: 3331.6)
---------------Finished!----------------
Finished at 2020/10/28 23:33:03 (took 00:01:04.1441066)
Status 200:    213025

RPS: 3480.9 (requests/second)
Max: 182ms
Min: 0ms
Avg: 0.5ms

  50%   below 0ms
  60%   below 0ms
  70%   below 0ms
  80%   below 0ms
  90%   below 1ms
  95%   below 3ms
  98%   below 6ms
  99%   below 8ms
99.9%   below 16ms
```

**总结**

1. 在内存较小时，串行 GC 吞吐量和其他类型 GC 基本没有差异
2. 在内存较大时，串行 GC 明显性能下降其他 GC 相差不大（此次压测 4G 内存时其他 3 个 GC 并没有明显差异）

#### 第 4 课

**写一段代码，使用 HttpClient 或 OkHttp 访问 [http://localhost:8801 ](http://localhost:8801/)，代码提交到 Github。**

```java
/// 省略包名
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * http请求
 * 2020-10-28 23:16
 */
public class HttpUtil {
    public static final String URL = "http://localhost:8801";

    public static final OkHttpClient HTTP_CLIENT = new OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS).connectTimeout(10, TimeUnit.SECONDS)
            .build();

    public static String get(String url) {

        Request request = new Request.Builder().url(url).build();
        try (Response response = HTTP_CLIENT.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            // TODO logger print
        }
        return "";
    }

    public static void main(String[] args) {
        System.out.println(get(URL));
    }
}
```









