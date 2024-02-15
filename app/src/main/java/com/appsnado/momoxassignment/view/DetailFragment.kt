package com.appsnado.momoxassignment.view

import android.app.DownloadManager.Request
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import coil.request.ImageRequest
import coil.size.Scale
import com.appsnado.momoxassignment.R
import com.appsnado.momoxassignment.databinding.DetailsFrgmentBinding

class DetailFragment : Fragment(R.layout.details_frgment) {

    private val args by navArgs<DetailFragmentArgs>()
    var fragmentFooterBinding : DetailsFrgmentBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = DetailsFrgmentBinding.bind(view)
        fragmentFooterBinding = binding

        binding.apply {

            image.load(args.details.urls.full) {
                crossfade(true)
                placeholder(R.drawable.image_placeholder)
                scale(Scale.FIT)

            }

            description.text = args.details.description

            val uri = Uri.parse(args.details.user.attributionUrl)
            val intent = Intent(Intent.ACTION_VIEW, uri)

            creater.apply {
                text = "Photo by ${args.details.user.name} on Unsplash"
                setOnClickListener {
                    context.startActivity(intent)
                }
                paint.isUnderlineText = true
            }
        }
    }
}