package com.mySchool.mobiledev_c196_pa.ui.detailviews;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mySchool.mobiledev_c196_pa.R;
import com.mySchool.mobiledev_c196_pa.data.entities.Instructor;
import com.mySchool.mobiledev_c196_pa.ui.addedit.AddEditInstructorFragment;
import com.mySchool.mobiledev_c196_pa.viewmodels.InstructorViewModel;

public class DetailedInstructorFragment extends Fragment {
    private static final String ID = "id";
    private long id;
    private InstructorViewModel instructorViewModel;
    private EditText name;
    private EditText phone;
    private EditText email;

    public DetailedInstructorFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id Instructor ID.
     * @return A new instance of fragment DetailedInstructorFragment.
     */
    public static DetailedInstructorFragment newInstance(long id) {
        DetailedInstructorFragment fragment = new DetailedInstructorFragment();
        Bundle args = new Bundle();
        args.putLong(ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle("Instructor Details");
        if (getArguments() != null) {
            id = getArguments().getLong(ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_instructor, container, false);
        name = v.findViewById(R.id.instructor_name);
        phone = v.findViewById(R.id.instructor_phone);
        email = v.findViewById(R.id.instructor_email);
        setEditTextViewOnly();
        instructorViewModel = new ViewModelProvider(requireActivity()).get(InstructorViewModel.class);
        instructorViewModel.getInstructorById(this.id).observe(getViewLifecycleOwner(), instructors -> {
            if (!instructors.isEmpty()) {
                    instructorViewModel.setInstructor(instructors.get(0));
                    populateFields(instructors.get(0));
            }
        });
        return v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.detail_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_detail_edit) {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.detail_view_host,
                            AddEditInstructorFragment.newInstance(this.id))
                    .addToBackStack("detail")
                    .commit();
        } else if (id == R.id.menu_detail_delete) {
            instructorViewModel.delete(instructorViewModel.getInstructor().getValue());
            getActivity().finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setEditTextViewOnly() {
        name.setClickable(false);
        name.setCursorVisible(false);
        name.setFocusable(false);
        name.setFocusableInTouchMode(false);
        name.setBackground(null);
        phone.setClickable(false);
        phone.setCursorVisible(false);
        phone.setFocusable(false);
        phone.setFocusableInTouchMode(false);
        phone.setBackground(null);
        email.setClickable(false);
        email.setCursorVisible(false);
        email.setFocusable(false);
        email.setFocusableInTouchMode(false);
        email.setBackground(null);
    }

    private void populateFields(Instructor instructor) {
        name.setText(instructor.getName());
        phone.setText(instructor.getPhone());
        email.setText(instructor.getEmail());
    }
}