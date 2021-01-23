package org.hy.microservice.post.common;

import java.util.List;

import org.hy.common.xml.SerializableDef;





/**
 * 响应二级数据的基础类
 *
 * @author      ZhengWei(HY)
 * @createDate  2021-01-08
 * @version     v1.0
 */
public class BaseResponseData<D> extends SerializableDef
{
    private static final long serialVersionUID = 1472565943960094849L;
    
    /** 页码 */
    private Long    pageIndex;
    
    /** 分页数  */
    private Long    pageSize;
    
    /** 分页数  */
    private Long    pageCount;
    
    /** 记录数  */
    private Long    recordCount;
    
    /** 分页数据 */
    private List<D> datas;
    
    /** 一条数据 */
    private D       data;

    
    
    /**
     * 获取：页码
     */
    public Long getPageIndex()
    {
        return pageIndex;
    }

    
    /**
     * 获取：分页数
     */
    public Long getPageSize()
    {
        return pageSize;
    }

    
    /**
     * 获取：分页数
     */
    public Long getPageCount()
    {
        return pageCount;
    }

    
    /**
     * 获取：记录数
     */
    public Long getRecordCount()
    {
        return recordCount;
    }

    
    /**
     * 设置：页码
     * 
     * @param pageIndex 
     */
    public void setPageIndex(Long pageIndex)
    {
        this.pageIndex = pageIndex;
    }

    
    /**
     * 设置：分页数
     * 
     * @param pageSize 
     */
    public void setPageSize(Long pageSize)
    {
        this.pageSize = pageSize;
    }

    
    /**
     * 设置：分页数
     * 
     * @param pageCount 
     */
    public void setPageCount(Long pageCount)
    {
        this.pageCount = pageCount;
    }

    
    /**
     * 设置：记录数
     * 
     * @param recordCount 
     */
    public void setRecordCount(Long recordCount)
    {
        this.recordCount = recordCount;
    }


    /**
     * 获取：分页数据
     */
    public List<D> getDatas()
    {
        return datas;
    }

    
    /**
     * 获取：一条数据
     */
    public D getData()
    {
        return data;
    }

    
    /**
     * 设置：分页数据
     * 
     * @param datas 
     */
    public void setDatas(List<D> datas)
    {
        this.datas = datas;
    }

    
    /**
     * 设置：一条数据
     * 
     * @param data 
     */
    public void setData(D data)
    {
        this.data = data;
    }
    
}
