package com.lizl.news.mvvm.activity

import androidx.lifecycle.Observer
import com.blankj.utilcode.util.ToastUtils
import com.lizl.news.R
import com.lizl.news.adapter.ShieldWordListAdapter
import com.lizl.news.custom.function.setOnItemLongClickListener
import com.lizl.news.dao.AppDatabase
import com.lizl.news.databinding.ActivityShieldWordsConfigBinding
import com.lizl.news.model.news.shieldword.ShieldWordModel
import com.lizl.news.mvvm.base.BaseActivity
import com.lizl.news.util.PopupUtil

class ShieldWordsConfigActivity : BaseActivity<ActivityShieldWordsConfigBinding>(R.layout.activity_shield_words_config)
{
    private val shieldWordsAdapter = ShieldWordListAdapter()

    override fun initView()
    {
        dataBinding.shieldAdapter = shieldWordsAdapter

        dataBinding.ctbTitle.setOnBackBtnClickListener { super.onBackPressed() }

        dataBinding.ctbTitle.setOnActionClickListener {
            PopupUtil.showInputPopup(getString(R.string.please_input_shield_word)) { input ->
                if (AppDatabase.instance.getShieldWordsDao().findShieldWord(input) != null)
                {
                    ToastUtils.showShort(R.string.notify_shield_word_exist)
                    return@showInputPopup
                }
                AppDatabase.instance.getShieldWordsDao().insert(ShieldWordModel().apply { word = input })
            }
        }

        shieldWordsAdapter.setOnItemLongClickListener { model: ShieldWordModel ->
            PopupUtil.showConfirmPopup(getString(R.string.sure_to_delete_shield_word)) {
                AppDatabase.instance.getShieldWordsDao().delete(model)
            }
        }
    }

    override fun initData()
    {
        AppDatabase.instance.getShieldWordsDao().getAllShieldWordsLiveData().observe(this, Observer {
            shieldWordsAdapter.setDiffNewData(it)
        })
    }
}