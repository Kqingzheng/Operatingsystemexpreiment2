# Operatingsystemexpreiment2


操作系统实验二


二、分页式存储器管理


2.1目的


1、熟练掌握分页式管理基本原理，并在实验过程中体现内存空间的配与回收、地址转换过程。


2、 掌握利用“位示图”管理内存与置换空间的分配与回收。


3、 掌握基本的位运算。


4、 掌握请求分页式存储管理基本原理，并在实验过程中体现内存与置换空间的分配与回收、地址转换以及缺页处理过程。


2.2内容


在实验 1 基础上实现分页式存储管理内存分配和地址转换过程。进一步实现请求分页式存储管理过程，包括内存和置换空间管理、地址转换以及缺页处理，能够体现 FIFO 和 LRU 算法思想。


1、建立一个位示图数据结构，用来模拟内存的使用情况。位示图是利用若干位的 0/1 值代表某类空间的占用状态的数据结构。在本实验中，位示图的位数与设定的物理块个数相同。程序启动时可利用一组随机 0 或 1 填充位示图，以模拟程序开始执行是内存已被占用状态。


2、在实验 1 基础上扩充 PCB，添加进程大小和页表，创建进程时输入进程大小，并根据程序中设定的页面大小为进程分配页表空间，并分配物理块。


3、输入当前执行进程所要访问的逻辑地址，并将其转换成相应的物理地址。


4、进程退出时，根据其页表内容将位示图对应位置的“1”回填为“0”。


5、扩充页表，将其变成支持请求和置换功能的二维页表（增加存在位等）。创建进程时可装入固定的前三页（或键盘输入初始装入页数，不同进程的装入个数可以不同），其余页装入到置换空间内（置换空间大小应为内存空间大小的1.5-2 倍，对其还需建立独立的置换空间位示图）。


6、分别采用FIFO或LRU置换算法对地址转换过程中可能遇到的缺页现象进行页面置换。可将多次地址转换过程中所涉及到的页号视为进程的页面访问序列，从而计算置换次数和缺页率。


2.3数据结构


定义PCB类
public class PCB implements Serializable {
    String name;
    int start;
    int length;
    int end;
    PCB next;
    boolean aBoolean = false;
}


定义扩充的PCB类
public class PCB_plus extends PCB implements Serializable {
    Extended_page_table[] page_tables;
    PCB_plus next;
    final static int init_page_size = 3;
    Queue<Integer> fifo = new LinkedList<Integer>();
    Queue<Integer> lru = new LinkedList<Integer>();
    List<Weight> list = new ArrayList<Weight>();
    String answer_fifo = "";
    String answer_lru = "";
    String answer_opt = "";
    int fifo_success = 0;//成功次数
    int fifo_fail = 0;//失败次数
    int lru_success = 0;//成功次数
    int lru_fail = 0;//失败次数
    int opt_fail = 0;
    int opt_success = 0;
}


定义位示图类
public class Bitmap implements Serializable {
    final static int piece_size = 1024;
    int map_size;
    int[][] bitmap;
    int[][] displaced_partition;
    int free_size;//位示图空闲块数
    int displaced_partition_size;//置换空间空闲块数
}


定义扩充页表类
public class Extended_page_table implements Serializable {
    int page_number;//页号
    int block_number;//内存块号
    boolean status_bit;//状态位
    boolean modify_bit;//修改位
    int external_storage_address;//置换空间块号
}
定义带权重的OPT算法辅助类
public class Weight implements Serializable {
    int pagename;
    int weight;


