package org.pyx.pyx.argorithm.tree;

import java.util.HashSet;
import java.util.Set;

/**
 * 给定一个二叉树，我们在树的节点上安装摄像头。
 *
 * 节点上的每个摄影头都可以监视其父对象、自身及其直接子对象。
 *
 * 计算监控树的所有节点所需的最小摄像头数量。
 *
 * 示例 1：
 *
 * 输入：[0,0,null,0,0]
 * 输出：1
 * 解释：如图所示，一台摄像头足以监控所有节点。
 * 示例 2：
 *
 * 输入：[0,0,null,0,null,0,null,null,0]
 * 输出：2
 * 解释：需要至少两个摄像头来监视树的所有节点。 上图显示了摄像头放置的有效位置之一。
 *
 * 提示：
 *
 * 给定树的节点数的范围是 [1, 1000]。
 * 每个节点的值都是 0。
 * @link https://leetcode-cn.com/problems/binary-tree-cameras/
 * @author pyx
 * @date 2019/5/27
 */
public class BinaryTreeCamera {

    int outAns;
    Set<TreeNode> covered;

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(0);
        root.right = null;
        root.left.left = new TreeNode(0);
        root.left.right = new TreeNode(0);
        root.left.left.left = new TreeNode(0);
        System.out.println(new BinaryTreeCamera().minCameraCover(root));
        System.out.println(new BinaryTreeCamera().minCameraCoverDfs(root));
    }

    /**
     *
     * dp 实现
     * @param root
     * @return
     */
    public int minCameraCover(TreeNode root) {
        int[] ans = solve(root);
        return Math.min(ans[1], ans[2]);
    }

    /**
     * dfs 实现
     * @param root
     * @return
     */
    public int minCameraCoverDfs(TreeNode root) {
        outAns = 0;
        covered = new HashSet<>();
        covered.add(null);
        dfs(root,null);
        return outAns;
    }

    /**
     * 动态规划的解决方案
     * Intuition
     *
     * Let's try to cover every node, starting from the top of the tree and working down. Every node considered must
     * be covered by a camera at that node or some neighbor.
     *
     * Because cameras only care about local state, we can hope to leverage this fact for an efficient solution.
     * Specifically, when deciding to place a camera at a node, we might have placed cameras to cover some subset of
     * this node, its left child, and its right child already.
     *
     * Algorithm
     *
     * Let solve(node) be some information about how many cameras it takes to cover the subtree at this node in
     * various states. There are essentially 3 states:
     *
     * [State 0] Strict subtree: All the nodes below this node are covered, but not this node.
     * [State 1] Normal subtree: All the nodes below and including this node are covered, but there is no camera here.
     * [State 2] Placed camera: All the nodes below and including this node are covered, and there is a camera here
     * (which may cover nodes above this node).
     * Once we frame the problem in this way, the answer falls out:
     *
     * To cover a strict subtree, the children of this node must be in state 1.
     * To cover a normal subtree without placing a camera here, the children of this node must be in states 1 or 2,
     * and at least one of those children must be in state 2.
     * To cover the subtree when placing a camera here, the children can be in any state.
     * Complexity Analysis
     * Time Complexity: O(N)O(N), where NN is the number of nodes in the given tree.
     * Space Complexity: O(H)O(H), where HH is the height of the given tree.
     * 想法：需要覆盖所有的节点，从头节点开始向下执行，考虑到每个节点必须要有当前的节点或临节点覆盖到；因为摄像头只关心本地状态，
     * 具体地说，当决定在节点上放置摄像机时，我们可能已经放置了摄像机来覆盖这个节点的某个子集，它的左子节点和它的右子节点。
     * 算法：
     * 通过 slove(node)为在不同状态下覆盖该节点的子树需要多少个摄像头的信息。本质上有三种状态:
     * 【状态 0】严格子树：所有的节点在这个节点下方是覆盖的，但是不是这个节点
     * 【状态 1】普通子树：当前节点的其所有子节点都呗覆盖了，但当前节点没有放置摄像头
     * 【状态 2】放置的摄像头：当前节点和其子节点都是被覆盖的，摄像头放置的点
     * 一旦我们用这种方法构造问题，答案就出来了:
     * 覆盖一个严格子树，孩子节点状态设置1，覆盖普通子树，子节点必须状态是1 或 2,并且至少一个子及诶但必须是状态2
     * 覆盖一个含摄像头的节点，子节点可以是任何状态
     * @param node
     * @return
     */
    private int[] solve(TreeNode node) {
        if(node == null){
            return new int[]{0,0,99999};
        }

        int[] L = solve(node.left);
        System.out.println("solve node left："+node.left);
        int[] R = solve(node.right);
        System.out.println("solve node right："+node.right);

        int mL12 = Math.min(L[1], L[2]);
        int mR12 = Math.min(R[1], R[2]);

        int d0 = L[1] + R[1];
        int d1 = Math.min(L[2] + mR12, R[2] + mL12);
        int d2 = 1 + Math.min(L[0], mL12) + Math.min(R[0], mR12);
        System.out.println(L[1]+" "+L[2]+" "+R[1]+" " + R[2]+" ");
        System.out.println(mL12+" "+mR12+" "+d0+" " + d1+" "+d2);
        return new int[]{d0, d1, d2};
    }

    /**
     * greedy 贪心算法
     * Instead of trying to cover every node from the top down, let's try to cover it from the bottom up -
     * considering placing a camera with the deepest nodes first, and working our way up the tree.
     * If a node has its children covered and has a parent, then it is strictly better to place the camera
     * at this node's parent.
     *
     * 算法
     * If a node has children that are not covered by a camera, then we must place a camera here. Additionally,
     * if a node has no parent and it is not covered, we must place a camera here.
     * @param node
     * @param parent
     * Complexity Analysis
     *
     * Time Complexity: O(N)O(N), where NN is the number of nodes in the given tree.
     * Space Complexity: O(H)O(H), where HH is the height of the given tree.
     */
    private void dfs(TreeNode node,TreeNode parent){
        if(node != null){
            dfs(node.left,node);
            dfs(node.right,node);

            if(parent == null && !covered.contains(node)||
                !covered.contains(node.left)||
                !covered.contains(node.right)){
                outAns++;
                covered.add(node);
                covered.add(parent);
                covered.add(node.left);
                covered.add(node.right);
            }
        }
    }
}
