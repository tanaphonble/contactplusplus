package ayp.aug.litecontacts.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import ayp.aug.litecontacts.R;
import ayp.aug.litecontacts.model.Contact;
import ayp.aug.litecontacts.model.PhoneBook;
import ayp.aug.litecontacts.model.PictureUtils;

/**
 * Created by Tanaphon on 8/9/2016.
 */
public class ContactListFragment extends Fragment {
    private static final int gridViewSpanSizeLandScape = 2;
    private static final int gridViewSpanSizePortrait = 3;
    private static final int REQUEST_CALL_ACTION = 4;
    public String contactPhoneNumberAfterAllowCallPermission;
    private RecyclerView contactRecyclerView;
    private ContactAdapter contactAdapter;
    private PhoneBook phoneBook;
    private GridLayoutManager gridLayoutManager;
    private boolean isWideWidth;
    private Callbacks callbacks;

    public interface Callbacks {
        public void onContactSelected(Contact contact);
    }
    public static ContactListFragment newInstance() {

        Bundle args = new Bundle();
        ContactListFragment fragment = new ContactListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CALL_ACTION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPhone(contactPhoneNumberAfterAllowCallPermission);
                } else {
                    Toast.makeText(getActivity(), R.string.denied_permission_to_call,
                            Toast.LENGTH_SHORT)
                            .show();
                }
                return;
        }
    }

    public void callPhone(String contactNumber) {
        try {
            Intent intentCall = new Intent(Intent.ACTION_CALL);
            intentCall.setData(Uri.parse("tel:" + contactNumber));
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) !=
                    PackageManager.PERMISSION_GRANTED) {
                contactPhoneNumberAfterAllowCallPermission =contactNumber;
                requestPermissions(new String[]{
                                Manifest.permission.CALL_PHONE},
                        REQUEST_CALL_ACTION);
                return;
            }
            startActivityForResult(intentCall, REQUEST_CALL_ACTION);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        updateGridViewSpanSize();
        gridLayoutManager = new GridLayoutManager(getActivity(), getGridViewSpanSize());
        View v = inflater.inflate(R.layout.fragment_contact_list, container, false);
        contactRecyclerView = (RecyclerView) v.findViewById(R.id.contact_recycle_view);
        contactRecyclerView.setLayoutManager(gridLayoutManager);
        updateListUI();
        return v;
    }

    private int getGridViewSpanSize() {
        if (isWideWidth)
            return gridViewSpanSizeLandScape;
        return gridViewSpanSizePortrait;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phoneBook = PhoneBook.getInstance(getActivity());
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.contact_list_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_item_new_contact);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_contact:
                Contact contact = new Contact();
                phoneBook.addContact(contact);
                callbacks.onContactSelected(contact);
                updateListUI();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateListUI() {
        updateGridViewSpanSize();
        PhoneBook phoneBook = PhoneBook.getInstance(getActivity());
        List<Contact> contacts = phoneBook.getContacts();
        gridLayoutManager.setSpanCount(getGridViewSpanSize());
        if (contactAdapter == null) {
            contactAdapter = new ContactAdapter(contacts);
            contactRecyclerView.setAdapter(contactAdapter);
        } else {
            contactAdapter.setContacts(phoneBook.getContacts());
            contactAdapter.notifyDataSetChanged();
        }
    }

    private void updateGridViewSpanSize() {
        Configuration configuration = getActivity().getResources().getConfiguration();
        int screenWidth = configuration.screenWidthDp;
        if (screenWidth >= 600)
            isWideWidth = true;
        else
            isWideWidth = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateListUI();
    }

    private class ContactHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {
        PhoneBook phoneBook = PhoneBook.getInstance(getActivity());
        File contactPhotoFile;
        ImageView contactPhotoImageView;
        TextView contactNameTextView;
        Contact contact;

        public ContactHolder(View itemView) {
            super(itemView);
            contactPhotoImageView =
                    (ImageView) itemView.findViewById(R.id.list_item_contact_photo_image_view);
            contactNameTextView =
                    (TextView) itemView.findViewById(R.id.list_item_contact_name_text_view);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void bind(Contact contact) {
            this.contact = contact;

            // set photo for contact holder
            contactPhotoFile = phoneBook.getPhotoFile(contact.getPhotoFileName());

            Bitmap contactPhotoBitmap = PictureUtils
                    .getScaledBitmap(contactPhotoFile.getPath(), getActivity());
            contactPhotoImageView.setImageBitmap(contactPhotoBitmap);
            contactPhotoFile = phoneBook.getPhotoFile(contact.getPhotoFileName());

            // set name for contact holder
            contactNameTextView.setText(contact.getName());
        }

        @Override
        public void onClick(View view) {
            callPhone(contact.getNumber());
        }

        @Override
        public boolean onLongClick(View view) {
            callbacks.onContactSelected(contact);
            return true;
        }

    }

    private class ContactAdapter extends RecyclerView.Adapter<ContactHolder> {

        private List<Contact> contacts;

        public ContactAdapter(List<Contact> contacts) {
            this.contacts = contacts;
        }

        @Override
        public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View v = layoutInflater.inflate(R.layout.list_item_contact, parent, false);
            return new ContactHolder(v);
        }

        @Override
        public void onBindViewHolder(ContactHolder holder, int position) {
            Contact contact = contacts.get(position);
            holder.bind(contact);
        }




        @Override
        public int getItemCount() {
            return contacts.size();
        }

        private void setContacts(List<Contact> contacts){
            this.contacts = contacts;
        }

    }
}

