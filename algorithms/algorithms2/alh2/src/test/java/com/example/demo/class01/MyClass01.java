package com.example.demo.class01;

import java.util.Arrays;

import ch.qos.logback.classic.turbo.TurboFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.engine.ConfigurationParameters;
import org.junit.platform.engine.support.hierarchical.ThrowableCollector;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: kavanLi-R7000
 * @create: 2023-12-01 14:56
 *
 * 冒泡排序与选择排序逻辑相反
 *
 * 冒泡排序（Bubble Sort）：
 *
 * 初始序列：5, 3, 8, 4, 2
 *
 * 第一轮：
 *
 * 5 和 3 交换，序列变为 3, 5, 8, 4, 2
 * 5 和 8 不交换
 * 8 和 4 交换，序列变为 3, 5, 4, 8, 2
 * 8 和 2 交换，序列变为 3, 5, 4, 2, 8
 * 第二轮：
 *
 * 3 和 5 不交换
 * 5 和 4 交换，序列变为 3, 4, 5, 2, 8
 * 5 和 2 交换，序列变为 3, 4, 2, 5, 8
 * 5 和 8 不交换
 * 以此类推，直到整个序列有序。
 *
 * 选择排序（Selection Sort）：
 *
 * 初始序列：5, 3, 8, 4, 2
 *
 * 第一轮：
 *
 * 从未排序部分选择最小的元素 2，与第一个元素 5 交换，序列变为 2, 3, 8, 4, 5
 * 第二轮：
 *
 * 从未排序部分选择最小的元素 3，与第二个元素 3（不变）交换，序列变为 2, 3, 8, 4, 5
 * 以此类推，直到整个序列有序。
 *
 * 初始序列：12, 54, 7, 87, 23, 9, 15, 63
 *
 * 第一轮：
 *
 * 从未排序的序列中选取第一个元素 54。
 * 比较 54 和 12，因为 54 大于 12，所以不需要交换，得到 12, 54, 7, 87, 23, 9, 15, 63。
 * 第二轮：
 *
 * 从未排序的序列中选取下一个元素 7。
 * 比较 7 和 54，需要交换它们的位置，得到 12, 7, 54, 87, 23, 9, 15, 63。
 * 再比较 7 和 12，需要交换它们的位置，得到 7, 12, 54, 87, 23, 9, 15, 63。
 * 第三轮：
 *
 * 从未排序的序列中选取下一个元素 87。
 * 87 大于已排序的所有元素，无需交换，得到 7, 12, 54, 87, 23, 9, 15, 63。
 * 第四轮：
 *
 * 从未排序的序列中选取下一个元素 23。
 * 比较 23 和 87，需要交换它们的位置，得到 7, 12, 54, 23, 87, 9, 15, 63。
 * 再比较 23 和 54，需要交换它们的位置，得到 7, 12, 23, 54, 87, 9, 15, 63。
 * 再比较 23 和 12，需要交换它们的位置，得到 7, 12, 23, 54, 87, 9, 15, 63。
 * 后续轮次：
 *
 * 以此类推，每一轮选取未排序序列中的元素，插入到已排序序列中的正确位置。
 * 最终，整个序列有序。这个例子希望更清晰地展示插入排序的过程。
 *
 *
冒泡排序、选择排序和插入排序都是基本的排序算法，它们的主要区别在于排序的方式和实现细节。

冒泡排序（Bubble Sort）：

工作原理： 通过不断地交换相邻元素将最大的元素逐渐移到数组末尾。
实现思路： 从数组的第一个元素开始，比较相邻的两个元素，如果它们的顺序不对，则交换它们，然后继续比较下一对相邻元素，直到整个数组有序。
时间复杂度： 平均情况和最坏情况都是O(n^2)。
选择排序（Selection Sort）：

工作原理： 通过选择未排序部分的最小元素并将其放到已排序部分的末尾。
实现思路： 从未排序的部分选择最小的元素，然后与未排序部分的第一个元素交换位置，这样就将最小的元素放在已排序部分的末尾，然后继续这个过程。
时间复杂度： 平均情况和最坏情况都是O(n^2)。
插入排序（Insertion Sort）：

工作原理： 通过构建有序序列，对于未排序的数据，在已排序序列中从后向前扫描，找到相应位置并插入。
实现思路： 将一个元素从未排序的部分取出，插入到已排序部分的合适位置，使得已排序部分仍然有序，然后继续这个过程。
时间复杂度： 平均情况和最坏情况都是O(n^2)，但在某些情况下，插入排序可能比冒泡排序和选择排序快。
区分方法：

冒泡排序关注的是相邻元素之间的比较和交换，通过不断地将最大的元素往后推。
选择排序关注的是找到未排序部分的最小元素，然后放到已排序部分的末尾。
插入排序关注的是将未排序部分的元素逐个插入到已排序部分的合适位置。
总体来说，冒泡排序和选择排序都是通过不断地找到最大或最小的元素来完成排序，而插入排序则是通过构建有序序列来完成。插入排序在处理小型数据集时可能比较高效，但在处理大型数据集时，通常不如高级排序算法。
 */
@SpringBootTest
public class MyClass01 {
    /* fields -------------------------------------------------------------- */


    /* constructors -------------------------------------------------------- */


    /* public methods ------------------------------------------------------ */

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            selectionSort(arr1);
            comparator(arr2);
            if (!isArrEqual(arr1, arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }

        System.out.println(succeed ? "yes yes yes" : "fvck fvck fvck");
        int[] arr = generateRandomArray(maxSize, maxValue);
        printArray(arr);
        selectionSort(arr);
        printArray(arr);
    }

    /**
     * easy
     * print an int to bit then stdout to console
     */
    @DisplayName("printInt2Bit")
    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3 })
    public static void printInt2Bit(int num) {

        System.out.println("start");
        long start = System.currentTimeMillis();
        for (int i = 31; i >= 0; i--) {
            System.out.print(((num & (1 << i)) == 0) ? '0' : '1');
        }
        long end = System.currentTimeMillis();
        System.out.println();
        System.out.println("done：" + (end - start));
    }

    /**
     * selectionSort
     */
    public static void selectionSort(int[] arr) {
        //先考虑边界条件
        if (null == arr || arr.length == 1) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            //
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
               if (arr[j] < arr[minIndex]) {
                   minIndex = j;
               }
            }

            swap(arr, i, minIndex);
        }
    }


    /**
     * swap
     * @param arr
     * @param i
     * @param j
     */
    @DisplayName("swap")
    @ParameterizedTest
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void swapPlus(int[] arr, int i, int j) {
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }

    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        // 1. first, generate arr which length equals max Size
        int[] arr = new int[(int) (Math.random() * ++maxSize)];
        // 2. second, fill random int number to arr.
        for (int i = 0; i < arr.length; i++) {
            // int range：-(maxValue-1) to maxValue
            arr[i] = (int)(Math.random() * ++maxValue) - (int)(Math.random() * maxValue);
        }
        return arr;
    }

    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    public static boolean isArrEqual(int[] arr1, int[] arr2) {
        // 3 boundary need to concern
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void printArray(int[] arr) {
        if (null == arr) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }





    /* private methods ----------------------------------------------------- */


    /* getters/setters ----------------------------------------------------- */

}
