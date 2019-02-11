package com.lin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lin.dao.BgmMapper;
import com.lin.dao.UserReportMapper;
import com.lin.dao.UserReportMapperCustom;
import com.lin.model.Bgm;
import com.lin.model.UserReport;
import com.lin.model.vo.Reports;
import com.lin.service.VideoService;
import com.lin.utils.PagedResult;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author lkmc2
 * @date 2019/2/11
 * @description 视频服务实现
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private BgmMapper bgmMapper;

    @Autowired
    private UserReportMapperCustom userReportMapperCustom;

    @Autowired
    private Sid sid;

    @Override
    public void addBgm(Bgm bgm) {
        String bgmId = sid.nextShort();

        bgm.setId(bgmId);
        // 将bgm插入数据库中
        bgmMapper.insert(bgm);
    }

    @Override
    public PagedResult queryBgmList(Integer page, Integer pageSize) {
        // 分页插件执行分页
        PageHelper.startPage(page, pageSize);

        // 创建查询条件
        Example example = new Example(Bgm.class);
        // 根据查询条件获取背景乐列表
        List<Bgm> bgmList = bgmMapper.selectByExample(example);

        // 获取插件的分页信息
        PageInfo<Bgm> pageInfo = new PageInfo<>(bgmList);

        // 创建自定义分页结果
        PagedResult pagedResult = new PagedResult();
        pagedResult.setTotal(pageInfo.getPages()); // 总页数
        pagedResult.setRows(bgmList); // 每行的数据
        pagedResult.setPage(page); // 当前页数
        pagedResult.setRecords(pageInfo.getTotal()); // 总记录数
        return pagedResult;
    }

    @Override
    public void delBgm(String bgmId) {
        // 根据主键删除背景乐
        bgmMapper.deleteByPrimaryKey(bgmId);
    }

    @Override
    public PagedResult queryReportList(Integer page, int pageSize) {
        // 分页插件执行分页
        PageHelper.startPage(page, pageSize);

        // 分页查询举报视频列表
        List<Reports> reportsList = userReportMapperCustom.selectAllVideoReport();

        // 获取插件的分页信息
        PageInfo<Reports> pageInfo = new PageInfo<>(reportsList);

        // 创建自定义分页结果
        PagedResult pagedResult = new PagedResult();
        pagedResult.setRows(reportsList); // 每行的数据
        pagedResult.setTotal(pageInfo.getPages()); // 总页数
        pagedResult.setRecords(pageInfo.getTotal()); // 总记录数
        pagedResult.setPage(page); // 当前页数
        return pagedResult;
    }

}
