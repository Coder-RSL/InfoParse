package com.feidian.sidebarTree.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.feidian.common.utils.StringUtils;
import com.feidian.sidebarTree.domain.SidebarTree;
import com.feidian.sidebarTree.service.ISidebarTreeService;
import com.feidian.sidebarTree.mapper.SidebarTreeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.feidian.common.utils.SecurityUtils.getLoginUser;
import static com.feidian.common.utils.SecurityUtils.getUserId;


/**
 * 侧边栏树的Service业务层处理
 *
 * @author feidian
 * @date 2022-07-01
 */
@Service
public class SidebarTreeServiceImpl implements ISidebarTreeService
{
    @Autowired
    private SidebarTreeMapper sidebarTreeMapper;

    /**
     * 查询侧边栏树的
     *
     * @param treeId 侧边栏树的主键
     * @return 侧边栏树的
     */
    @Override
    public SidebarTree selectSidebarTreeByTreeId(Long treeId)
    {
        return sidebarTreeMapper.selectSidebarTreeByTreeId(treeId);
    }

    /**
     * 查询侧边栏树的列表
     *
     * @param sidebarTree 侧边栏树的
     * @return 侧边栏树的
     */
    @Override
    public List<SidebarTree> selectSidebarTreeList(SidebarTree sidebarTree)
    {
        return sidebarTreeMapper.selectSidebarTreeList(sidebarTree);
    }

    /**
     * 新增侧边栏树的
     *
     * @param sidebarTree 侧边栏树的
     * @return 结果
     */
    @Override
    public int insertSidebarTree(SidebarTree sidebarTree)
    {
        sidebarTree.setCreateBy(getUserId().toString());
        return sidebarTreeMapper.insertSidebarTree(sidebarTree);
    }

    /**
     * 修改侧边栏树的
     *
     * @param sidebarTree 侧边栏树的
     * @return 结果
     */
    @Override
    public int updateSidebarTree(SidebarTree sidebarTree)
    {
        Long treeId = sidebarTree.getTreeId();
        SidebarTree treeByTreeId = sidebarTreeMapper.selectSidebarTreeByTreeId(treeId);
        String createBy = treeByTreeId.getCreateBy();
        try {
            if (!StringUtils.equals(createBy,getUserId().toString())&&getUserId()!=1)
                return -1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        sidebarTree.setUpdateBy(createBy);
        return sidebarTreeMapper.updateSidebarTree(sidebarTree);
    }

    /**
     * 批量删除侧边栏树的
     *
     * @param treeIds 需要删除的侧边栏树的主键
     * @return 结果
     */
    @Override
    public int deleteSidebarTreeByTreeIds(Long[] treeIds)
    {
        Long parentTreeId = sidebarTreeMapper.selectAllParentTreeId();
        for(Long id:treeIds){
            if(parentTreeId.contains(id)||Objects.equals(id,0L)){
                try {
                    throw new Exception("禁止删除父节点");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            SidebarTree treeByTreeId = sidebarTreeMapper.selectSidebarTreeByTreeId(id);
            String createBy = treeByTreeId.getCreateBy();
            try {
                if (!StringUtils.equals(createBy,getUserId().toString())||getUserId()!=1)
                    throw new Exception("存在节点无删除权限");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return sidebarTreeMapper.deleteSidebarTreeByTreeIds(treeIds);
    }

    /**
     * 删除侧边栏树的信息
     *
     * @param treeId 侧边栏树的主键
     * @return 结果
     */
    @Override
    public int deleteSidebarTreeByTreeId(Long treeId)
    {
        return sidebarTreeMapper.deleteSidebarTreeByTreeId(treeId);
    }

    /**
     * 获取子节点列表
     * @param t 返回的总的SidebarTree对象，作为侧边栏树
     * @return 返回子节点列表
     */
    @Override
    public List<SidebarTree> getChildrenList(SidebarTree t) {
        List<SidebarTree> list = sidebarTreeMapper.selectTreeNodeByParentId(t.getTreeId(),t.getTreeType());
        return list;
    }

    /**
     * 查询树的第一层节点
     *
     * @param tree 传入的参数
     * @return 返回所有第一层节点
     */
    @Override
    public List<SidebarTree> selectTreeList(SidebarTree tree) {
        tree.setParentId(0L);
        return sidebarTreeMapper.selectSidebarTreeList(tree);
    }

    /**
     * 查询树的所有子节点
     * @return 返回的所有节点
     */
    @Override
    public SidebarTree selectAllTreeNode(boolean isAdmin,SidebarTree tree) {
//        List<SidebarTree> trees = selectTreeList(tree);//tree是条件查询
//        //trees获得的节点是 1 or 2 的节点，更具Type来判断
//        tree.setChildren(trees);

        tree.setTreeId(0L);
        getChildrenNode(isAdmin,tree);

        return tree;//父亲节点
    }

    /**
     * 获取子节点
     *
     * @param isAdmin 管理员
     * @param tree    侧边栏树
     */
    public void getChildrenNode(boolean isAdmin,SidebarTree tree){

        List<SidebarTree> childrenTree = sidebarTreeMapper.selectTreeNodeByParentId(tree.getTreeId(), tree.getTreeType());

        if(childrenTree.size()!=0){
            //若有子节点
            if(!isAdmin){
                for(int i=childrenTree.size()-1; i>=0 ; i--){
                    // 当不是管理员时。删除isShow为0且创建者不是自己的节点
                    SidebarTree sidebarTree =childrenTree.get(i);
                    if(sidebarTree.getIsShow() == 0&& !Objects.equals(sidebarTree.getCreateBy(), getLoginUser().getUsername())){
                        childrenTree.remove(sidebarTree);
                    }
                }
            }

            for (SidebarTree sidebarTree : childrenTree) {
                getChildrenNode(isAdmin,sidebarTree);
            }
        }
        tree.setChildren(childrenTree);
    }

    @Override
    public SidebarTree recursionAllNodes(boolean isAdmin, SidebarTree tree,List<SidebarTree> nextNode,int treeType) {
        //递归
        if(nextNode.size()==0){
            return null;
        }

        for (int i = 0; i < nextNode.size(); i++) {
            List<SidebarTree> trees = sidebarTreeMapper.selectTreeNodeByParentId(nextNode.get(i).getTreeId(),treeType);
            //通过父节点获取子节点
            for (int j=trees.size()-1;j>=0;j--) {
                SidebarTree sidebarTree =trees.get(j);
                //若不为管理员，将子节点删除不显示的节点
                if(sidebarTree.getIsShow()!=null && sidebarTree.getIsShow()==0){
                    if(!isAdmin)
                        trees.remove(j);
                }
            }


            if(trees.size()!=0){
                nextNode.get(i).setChildren(trees);
            }
            else{
                return null;
            }
        }
        return tree;
    }
}
