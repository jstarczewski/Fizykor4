package com.clakestudio.pc.fizykor.flashcards

import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.core.view.GestureDetectorCompat
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.clakestudio.pc.fizykor.R
import com.clakestudio.pc.fizykor.databinding.FragmentFlashCardsBinding
import kotlinx.android.synthetic.main.fragment_flash_cards.*
import kotlinx.android.synthetic.main.fragment_flash_cards.view.*

class FlashCardsFragment : androidx.fragment.app.Fragment(), GestureDetector.OnGestureListener, View.OnTouchListener {

    private lateinit var viewFragmentBinding: FragmentFlashCardsBinding
    private lateinit var gestureDetectorCompat: GestureDetectorCompat

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        viewFragmentBinding = FragmentFlashCardsBinding.inflate(inflater, container, false).apply {
            viewmodel = (activity as FlashCardsActivity).obtainViewModel().apply {
                animatePreviousFlashCardEvent.observe(this@FlashCardsFragment, Observer { animatePrevious() })
                animateNewFlashCardEvent.observe(this@FlashCardsFragment, Observer { animateNext() })
                navigationToast.observe(this@FlashCardsFragment, Observer { showNavigationToast() })
            }

        }

        return viewFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupGestureDetector()
        setupCheckBox()

        showNavigationToast()

    }

    override fun onResume() {
        super.onResume()
        viewFragmentBinding.viewmodel?.start()
    }

    private fun setupGestureDetector() {

        gestureDetectorCompat = GestureDetectorCompat(this.activity, this)
        viewFragmentBinding.root.cvFlashCard.setOnTouchListener(this)

    }

    private fun showNavigationToast() {
        for (x in 0..3)
            Toast.makeText(context, getString(R.string.flashCards_navigation), Toast.LENGTH_LONG).show()
    }

    private fun animateNext() {
        cvFlashCard.startAnimation(AnimationUtils.loadAnimation(context, R.anim.card_view_transition_out_to_right))
    }

    private fun animatePrevious() {
        cvFlashCard.startAnimation(AnimationUtils.loadAnimation(context, R.anim.card_view_transition_out_to_left))
    }

    private fun setupCheckBox() {
        viewFragmentBinding.cbMode.setOnClickListener { viewFragmentBinding.viewmodel?.isMaturaMode = viewFragmentBinding.cbMode.isChecked }
    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        viewFragmentBinding.viewmodel?.determineAnimation(e1!!.x, e2!!.x)
        return true
    }

    override fun onShowPress(e: MotionEvent?) {

    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent?) {
        viewFragmentBinding.viewmodel?.switchMathViewVisibility()
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return gestureDetectorCompat.onTouchEvent(event)
    }


    companion object {
        fun newInstance() = FlashCardsFragment()
    }

}


