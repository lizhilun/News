package com.lizl.news.adapter

import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lizl.news.R
import com.lizl.news.databinding.ItemZhihuQuestionBinding
import com.lizl.news.model.zhihu.ZhiHuQuestionModel
import com.lizl.news.util.PopupUtil
import com.zzhoujay.richtext.CacheType
import com.zzhoujay.richtext.ImageHolder
import com.zzhoujay.richtext.RichText
import com.zzhoujay.richtext.RichType
import com.zzhoujay.richtext.ig.DefaultImageGetter

class ZhiHuQuestionListAdapter(questionList: List<ZhiHuQuestionModel>) :
        BaseQuickAdapter<ZhiHuQuestionModel, BaseViewHolder>(R.layout.item_zhihu_question, questionList.toMutableList())
{

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int)
    {
        DataBindingUtil.bind<ItemZhihuQuestionBinding>(viewHolder.itemView)
    }

    override fun convert(helper: BaseViewHolder, item: ZhiHuQuestionModel)
    {
        helper.getBinding<ItemZhihuQuestionBinding>()?.apply {

            questionModel = item

            tvTitle.paint.isFakeBoldText = true

            RichText.from(item.answer) // 数据源
                .autoFix(true) // 是否自动修复，默认true
                .type(RichType.html).autoPlay(true) // gif图片是否自动播放
                .showBorder(false) // 是否显示图片边框
                .imageGetter(DefaultImageGetter()).scaleType(ImageHolder.ScaleType.center_crop) // 图片缩放方式
                .size(ImageHolder.MATCH_PARENT, ImageHolder.WRAP_CONTENT) // 图片占位区域的宽高
                .noImage(false) // 不显示并且不加载图片
                .resetSize(false) // 默认false，是否忽略img标签中的宽高尺寸（只在img标签中存在宽高时才有效），true：忽略标签中的尺寸并触发SIZE_READY回调，false：使用img标签中的宽高尺寸，不触发SIZE_READY回调
                .clickable(true) // 是否可点击，默认只有设置了点击监听才可点击
                .imageClick { imageUrls, position -> PopupUtil.showImageViewerPopup(imageUrls, position) } // 设置图片点击回调
                .urlClick { url -> false } // 设置链接点击回调
                .cache(CacheType.all) // 缓存类型，默认为Cache.ALL（缓存图片和图片大小信息和文本样式信息）
                .into(tvContent) // 设置目标TextView

            executePendingBindings()
        }
    }
}