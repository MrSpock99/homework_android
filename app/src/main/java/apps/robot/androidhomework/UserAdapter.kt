package apps.robot.androidhomework

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter(private val clickListener: UsersListClickListener) :
    ListAdapter<User, UserAdapter.UserHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder =
        UserHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    override fun submitList(list: List<User>?) {
        super.submitList(
            if (list != null) {
                ArrayList(list)
            } else
                null
        )
    }

    class UserHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(user: User, listener: UsersListClickListener) {
            view.iv_photo.setImageResource(user.photo)
            view.tv_name.text = user.name
            view.tv_id.text = user.id.toString()
            view.tv_interests.text = user.interests
            view.setOnClickListener {
                listener.onClick(1)
            }
        }
    }

    class UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User?, newItem: User?): Boolean {
            return oldItem?.id == newItem?.id
        }

        override fun areContentsTheSame(oldItem: User?, newItem: User?): Boolean {
            return oldItem == newItem
        }
    }
}
