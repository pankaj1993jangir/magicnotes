package notes.magic.app.magiclanenotes.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import notes.magic.app.magiclanenotes.NotesActivity;
import notes.magic.app.magiclanenotes.R;
import notes.magic.app.magiclanenotes.model.Note;
import notes.magic.app.magiclanenotes.utilities.DateUtil;
import notes.magic.app.magiclanenotes.utilities.StringUtil;

/**
 * Created by pankaj on 7/12/17.
 */

public class NotesAdapter extends ArrayAdapter<Note> {
    private NotesActivity activity;
    private LayoutInflater inflater;

    public NotesAdapter(NotesActivity activity, @LayoutRes int resource, List<Note> notes) {
        super(activity, resource, notes);
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_view_item_note, null);
            holder = new ViewHolder(convertView) {
            };
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Note note = getItem(position);
        holder.title.setText(note.getTitle());
        holder.note.setText(note.getNote());
        holder.createTime.setText(activity.getString(R.string.created, DateUtil.getFormattedTime(note.getCreationTime())));
        String updateTime = DateUtil.getFormattedTime(note.getUpdateTime());
        holder.updateTime.setVisibility(View.GONE);
        if (!StringUtil.isNullOrEmpty(updateTime)) {
            holder.updateTime.setText(activity.getString(R.string.updated, DateUtil.getFormattedTime(note.getUpdateTime())));
            holder.updateTime.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    static class ViewHolder {
        TextView title;
        TextView note;
        TextView createTime;
        TextView updateTime;

        public ViewHolder(View convertView) {
            bindViews(convertView);
            convertView.setTag(this);
        }

        private void bindViews(View convertView) {
            title = (TextView) convertView.findViewById(R.id.tvTitle);
            note = (TextView) convertView.findViewById(R.id.tvNote);
            createTime = (TextView) convertView.findViewById(R.id.tvCreateTime);
            updateTime = (TextView) convertView.findViewById(R.id.tvUpdateTime);
        }
    }
}
