package com.example.facebook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CommentFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var commentAdapter: CommentAdapter
    private val commentList = mutableListOf<Comment>()

    companion object {
        private const val ARG_POST_ID = "post_id"

        fun newInstance(postId: String) = CommentFragment().apply {
            arguments = Bundle().apply { putString(ARG_POST_ID, postId) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_comment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val postId = arguments?.getString(ARG_POST_ID) ?: return

        recyclerView = view.findViewById(R.id.commentRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        commentAdapter = CommentAdapter(commentList)
        recyclerView.adapter = commentAdapter

        // EditText + Button thêm comment
        val input = view.findViewById<EditText>(R.id.commentInput)
        val sendBtn = view.findViewById<Button>(R.id.sendCommentBtn)

        sendBtn.setOnClickListener {
            val content = input.text.toString().trim()
            if (content.isNotEmpty()) {
                val comment = Comment(
                    id = System.currentTimeMillis().toString(),
                    postId = postId,
                    userName = "Bạn", // hoặc lấy tên người dùng thực
                    content = content,
                    createdAt = "Vừa xong"
                )
                commentList.add(comment)
                commentAdapter.notifyItemInserted(commentList.size - 1)
                recyclerView.scrollToPosition(commentList.size - 1)
                input.text.clear()
            }
        }

        input.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                sendBtn.performClick()
                true
            } else false
        }

        // TODO: Nếu muốn, bạn có thể load comment cũ từ server hoặc DB theo postId
    }
}
