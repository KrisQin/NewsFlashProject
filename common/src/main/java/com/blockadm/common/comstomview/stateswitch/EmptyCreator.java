package com.blockadm.common.comstomview.stateswitch;

import android.content.Context;
import android.support.annotation.NonNull;

public interface EmptyCreator {
	@NonNull
	StateEmptyInterface createStateEmpty(@NonNull Context context, @NonNull StateLayout layout);
}
