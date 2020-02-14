package com.gsolano.baseballui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.slide_layout.view.*

class Slider_Adapter(var context: Context): PagerAdapter() {

    private val layoutInfalter: LayoutInflater

    init {
        layoutInfalter = LayoutInflater.from(context)
    }

    var slide_background: IntArray = intArrayOf(R.drawable.gradient_intro1,R.drawable.gradient,R.drawable.gradient_intro2)
    var slide_images: IntArray = intArrayOf(R.drawable.ic_baseball,R.drawable.baseballsplash,R.drawable.ic_baseball)
    var slide_headings = arrayOf(R.string.IntroBienvenido,R.string.IntroFruta,R.string.IntroRedsocial)
    var slide_descriptions = arrayOf(R.string.IntroDescBienvenido,R.string.IntroDescFruta,R.string.IntroDescRedsocial)

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as (RelativeLayout)
    }

    override fun getCount(): Int {
        return slide_headings.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = layoutInfalter.inflate(R.layout.slide_layout, container, false)
        view.slideLayoutRelativeLayout.setBackgroundResource(slide_background[position])
        view.slideLayoutImageView.setImageResource(slide_images[position])
        view.slideLayoutTv1.setText(slide_headings[position])
        view.slideLayoutTv2.setText(slide_descriptions[position])
        if(position == 0){
            view.slideLayoutTvPoint1.setTextColor(context.resources.getColor(R.color.colorWhite))
            view.slideLayoutTvPoint2.setTextColor(context.resources.getColor(R.color.colorTransparentWhite))
            view.slideLayoutTvPoint3.setTextColor(context.resources.getColor(R.color.colorTransparentWhite))
        }
        if(position == 1){
            view.slideLayoutTvPoint1.setTextColor(context.resources.getColor(R.color.colorTransparentWhite))
            view.slideLayoutTvPoint2.setTextColor(context.resources.getColor(R.color.colorWhite))
            view.slideLayoutTvPoint3.setTextColor(context.resources.getColor(R.color.colorTransparentWhite))
        }
        if(position == 2){
            view.slideLayoutTvPoint1.setTextColor(context.resources.getColor(R.color.colorTransparentWhite))
            view.slideLayoutTvPoint2.setTextColor(context.resources.getColor(R.color.colorTransparentWhite))
            view.slideLayoutTvPoint3.setTextColor(context.resources.getColor(R.color.colorWhite))
        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as (RelativeLayout))
    }
}


