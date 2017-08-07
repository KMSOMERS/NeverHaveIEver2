package uk.co.kmsomers.neverhaveiever.views.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import uk.co.kmsomers.neverhaveiever.R;

/**
 * Created by kizer on 06/08/2017.
 */

public class InstructionsDialogFragment extends DialogFragment {

    public static InstructionsDialogFragment getInstance (){
        InstructionsDialogFragment dialogFragment = new InstructionsDialogFragment();
        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.instructions_title);
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setMessage(R.string.quiz_instructions);

        return builder.create();
    }
}
