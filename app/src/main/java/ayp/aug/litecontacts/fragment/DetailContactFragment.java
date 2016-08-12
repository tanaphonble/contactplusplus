package ayp.aug.litecontacts.fragment;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.util.UUID;

import ayp.aug.litecontacts.R;
import ayp.aug.litecontacts.model.Contact;
import ayp.aug.litecontacts.model.PhoneBook;
import ayp.aug.litecontacts.model.PictureUtils;

/**
 * Created by Tanaphon on 8/10/2016.
 */
public class DetailContactFragment extends Fragment {
    private static final String CONTACT_UUID = "CONTACT_UUID";
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private UUID contactUUID;
    private Contact contact;
    private PhoneBook phoneBook;
    private File contactPhotoFile;
    private Callbacks callbacks;
    final Intent captureContactPhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


    ImageView contactPhotoImageView;
    ImageButton captureContactPhotoImageButton;
    EditText contactNameEditText;
    EditText contactNumberEditText;
    EditText contactEmailEditText;
    Button deleteContactButton;

    public interface Callbacks {
        public void onContactDeleted();

        public void onContactUpdate(Contact contact);

    }

    @Override
    public void onResume() {
        super.onResume();
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

    public static DetailContactFragment newInstance(UUID uuid) {

        Bundle args = new Bundle();

        DetailContactFragment fragment = new DetailContactFragment();
        args.putSerializable(CONTACT_UUID, uuid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phoneBook = PhoneBook.getInstance(getActivity());
        contactUUID = (UUID) getArguments().getSerializable(CONTACT_UUID);
        contact = phoneBook.getContactById(contactUUID);
    }


    private boolean hasPermissionToTakePhoto() {
        return contactPhotoFile != null && captureContactPhotoIntent.resolveActivity(getActivity().getPackageManager()) != null;
    }

    private void configContactPhotoImageView() {

        contactPhotoFile = phoneBook.getPhotoFile(contact.getPhotoFileName());
        Bitmap bitmap = PictureUtils.getScaledBitmap(contactPhotoFile.getPath(), getActivity());
        contactPhotoImageView.setImageBitmap(bitmap);
    }

    private void configCaptureContactPhotoImageButton() {
        captureContactPhotoImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasPermissionToTakePhoto()) {
                    contactPhotoFile = phoneBook.getPhotoFile(contact.getPhotoFileName());
                    Uri uri = Uri.fromFile(contactPhotoFile);
                    captureContactPhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                }
                startActivityForResult(captureContactPhotoIntent, REQUEST_IMAGE_CAPTURE);
            }
        });
    }

    private void configContactNameEditText() {
        contactNameEditText.setText(contact.getName());
        contactNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                contact.setName(charSequence.toString());
                updateContact();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void updateContact() {
        phoneBook.updateContact(contact);
        callbacks.onContactUpdate(contact);
    }

    private void configContactNumberEditText() {
        contactNumberEditText.setText(contact.getNumber());
        contactNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                contact.setNumber(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void configContactEmailEditText() {
        contactEmailEditText.setText(contact.getEmail());
        contactEmailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                contact.setEmail(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    private void configDeleteContactButton() {
        deleteContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneBook.deleteContact(contactUUID);
                callbacks.onContactDeleted();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        phoneBook.updateContact(contact);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_detail, container, false);
        contactPhotoImageView = (ImageView) view.findViewById(R.id.contact_photo_image_view);
        captureContactPhotoImageButton =
                (ImageButton) view.findViewById(R.id.photo_capture_image_button);
        contactNameEditText = (EditText) view.findViewById(R.id.contact_name_edit_text);
        contactNumberEditText = (EditText) view.findViewById(R.id.contact_number_edit_text);
        contactEmailEditText = (EditText) view.findViewById(R.id.contact_email_edit_text);
        deleteContactButton = (Button) view.findViewById(R.id.delete_contact_button);
        configContactPhotoImageView();
        configCaptureContactPhotoImageButton();
        configContactNameEditText();
        configContactNumberEditText();
        configContactEmailEditText();
        configDeleteContactButton();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;
        if (requestCode == REQUEST_IMAGE_CAPTURE)
            updateContactPhotoView();
    }

    private void updateContactPhotoView() {
        if (contactPhotoFile == null || !contactPhotoFile.exists())
            contactPhotoImageView.setImageDrawable(null);
        else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(contactPhotoFile.getPath(), getActivity());
            contactPhotoImageView.setImageBitmap(bitmap);
        }
    }
}