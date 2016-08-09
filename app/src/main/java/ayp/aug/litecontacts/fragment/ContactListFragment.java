package ayp.aug.litecontacts.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ayp.aug.litecontacts.R;
import ayp.aug.litecontacts.model.Contact;

/**
 * Created by Tanaphon on 8/9/2016.
 */
public class ContactListFragment extends Fragment {
    private RecyclerView contactRecyclerView;

    public static ContactListFragment newInstance() {

        Bundle args = new Bundle();

        ContactListFragment fragment = new ContactListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contact_list, container, false);
        contactRecyclerView = (RecyclerView) v.findViewById(R.id.contact_recycle_view);
        contactRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        contactRecyclerView.setAdapter(new ContactAdapter());
        return v;
    }

    private class ContactHolder extends RecyclerView.ViewHolder {

        public ContactHolder(View itemView) {
            super(itemView);
        }
    }

    private class ContactAdapter extends RecyclerView.Adapter<ContactHolder> implements View.OnClickListener, View.OnLongClickListener {

        @Override
        public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(ContactHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        @Override
        public void onClick(View view) {

        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }
}

