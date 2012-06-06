package dtd.phs.sil;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import dtd.phs.sil.data.DataCenter;
import dtd.phs.sil.entities.MessageItem;
import dtd.phs.sil.entities.SentMessageItem;
import dtd.phs.sil.entities.SentMessagesList;
import dtd.phs.sil.ui.OnListItemTouchListener;
import dtd.phs.sil.utils.Logger;

public abstract class SentMessagesAdapter extends MessageAdapter {

//	private static final int STUB_AVATAR = R.drawable.contact;
	public SentMessagesAdapter(Context applicationContext,SentMessagesList sentMessagesList) {
		super(applicationContext,sentMessagesList);
	}

	protected void modifyDatabaseOnDeleteButtonClick(long id) {
		DataCenter.removeSentItem(context,id);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return super.getView(position,convertView,R.layout.sent_item);

	}

	protected void processOnItemTouch(int position, View v) {
		v.setOnTouchListener(new OnListItemTouchListener(position,v) {
			
			@Override
			public void onSwipe(View view, int position) {
				View delete = view.findViewById(R.id.btDelete);
				if ( delete.getVisibility() == View.VISIBLE) {
					makeButtonDispear(delete, position);			
//					updateMessageDeliveredIcon(view.findViewById(R.id.ivFailed), (SentMessageItem) messages.get(position));
				} else {
					displayingDeleteButton.set(position, true);
					delete.startAnimation(occAnim);
					delete.setVisibility(View.VISIBLE);
					view.findViewById(R.id.ivFailed).setVisibility(View.GONE);
				}
			}
			
			@Override
			public void onLongClick(int position) {
				onItemLongClick(position);
			}
			
			@Override
			public void onClick(View view, int position) {
				View delete = view.findViewById(R.id.btDelete);
				if ( delete.getVisibility() == View.VISIBLE) {
					makeButtonDispear(delete,position);
//					updateMessageDeliveredIcon(view.findViewById(R.id.ivFailed), (SentMessageItem) messages.get(position));
				} else {
					onItemClick(view,position);
				}
			}
		});
	}

	protected void updateView(ViewHolder holder, final MessageItem message, int position) {
//		holder.avatar.setImageResource(STUB_AVATAR);
		holder.contact.setText(message.getContact());
		holder.content.setText(message.getContent());
		updateDeleteButton(holder.delete, position, message);
		updateStatus(holder, message);
//		updateMessageDeliveredIcon(holder.failedIcon, (SentMessageItem)message);
	}

	private void updateStatus(ViewHolder holder, final MessageItem message) {
		SentMessageItem item = (SentMessageItem)message;
		if ( item.isSent() ) {
			Logger.logInfo("Set status to RED");
			holder.status.setTextColor(context.getResources().getColor(R.color.mainTextColor));
			
		} else {
			Logger.logInfo("Set status to NORMAL");
			holder.status.setTextColor(context.getResources().getColor(R.color.red));
		}
		holder.status.setText(item.getStatus(context));
	}

//	private void updateMessageDeliveredIcon(View failedIcon,
//			SentMessageItem message) {
//		if ( ! message.isSent() ) {
//			failedIcon.setVisibility(View.VISIBLE );
//		} else failedIcon.setVisibility(View.INVISIBLE );
//	}

	protected void createHolder(View v, ViewHolder holder) {
		super.createHolder(v,holder);
//		holder.failedIcon = (ImageView) v.findViewById(R.id.ivFailed);
	}
}
