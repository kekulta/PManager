package com.kekulta.pmanager.list.presentation.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.kekulta.pmanager.R
import com.kekulta.pmanager.databinding.SiteviewLayoutBinding
import com.kekulta.pmanager.list.presentation.ui.loadFavicon
import com.kekulta.pmanager.list.presentation.vo.SiteVo
import com.kekulta.pmanager.shared.utils.getDrawable
import com.kekulta.pmanager.shared.utils.getMaterialColorStateList
import com.kekulta.pmanager.shared.utils.gone
import com.kekulta.pmanager.shared.utils.show
import com.google.android.material.R as Rm

class SiteView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding: SiteviewLayoutBinding =
        SiteviewLayoutBinding.inflate(LayoutInflater.from(context), this)

    init {
        orientation = HORIZONTAL
        background = getDrawable(R.drawable.outer_background)
        backgroundTintList = getMaterialColorStateList(Rm.attr.colorSurfaceContainerHigh)

        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    fun bind(vo: SiteVo) {
        binding.faviconIv.loadFavicon(vo.name)
        binding.siteNameTv.text = vo.name

        if (vo.login.isNullOrBlank()) {
            binding.loginTv.gone()
        } else {
            binding.loginTv.text = vo.login
            binding.loginTv.show()
        }
    }
}