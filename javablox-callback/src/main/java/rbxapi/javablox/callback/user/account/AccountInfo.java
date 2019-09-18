package rbxapi.javablox.callback.user.account;

import rbxapi.javablox.api.user.account.info.Birthdate;
import rbxapi.javablox.client.CallbackAdapter;
import rbxapi.javablox.client.JavabloxResponseCallback;
import rbxapi.javablox.model.Description;
import rbxapi.javablox.model.Gender;
import rbxapi.javablox.model.JavabloxException;
import rbxapi.javablox.svc.AccountInfoService;

public class AccountInfo implements rbxapi.javablox.api.user.account.AccountInfo {
    private AccountInfoService svc;

    public AccountInfo(AccountInfoService svc) {
        this.svc = svc;
    }

    @Override
    public void getBirthdate(JavabloxResponseCallback<Birthdate> callback) {
        svc.getBirthdate().enqueue(new CallbackAdapter<>(callback) {
            @Override
            public Birthdate convertResponse(Birthdate response) {
                return response;
            }

            @Override
            public void onExceptions(JavabloxException[] ez, int status) {
                callback.onAccessDenied(ez[0]);
            }
        });
    }

    @Override
    public void updateBirthdate(Birthdate birthdate, JavabloxResponseCallback<Void> callback) {
        svc.updateBirthdate(birthdate).enqueue(new CallbackAdapter<>(callback) {
            @Override
            public Void convertResponse(Void response) {
                return response;
            }

            @Override
            public void onExceptions(JavabloxException[] ez, int status) {
                for (JavabloxException e : ez)
                    switch (e.getCode()) {
                        case 1:
                            callback.onUserNotFound(e);
                            break;
                        case 4:
                        case 5:
                            callback.onInvalidRequestBody(e);
                            break;
                        case 0:
                            if (status == 401) callback.onAccessDenied(e);
                            else callback.onUnknownError(e);
                            break;
                        case 2:
                            callback.onPinLocked(e);
                    }
            }
        });
    }

    @Override
    public void getDescription(JavabloxResponseCallback<String> callback) {
        svc.getDescription().enqueue(new CallbackAdapter<>(callback) {
            @Override
            public String convertResponse(Description response) {
                return response.getDescription();
            }

            @Override
            public void onExceptions(JavabloxException[] ez, int status) {
                for (JavabloxException e : ez)
                    if (e.getCode() == 0) callback.onAccessDenied(e);
                    else callback.onUserNotFound(e);
            }
        });
    }

    @Override
    public void setDescription(String description, JavabloxResponseCallback<Void> callback) {
        svc.setDescription(new Description(description)).enqueue(new CallbackAdapter<>(callback) {
            @Override
            public Void convertResponse(Void response) {
                return response;
            }

            @Override
            public void onExceptions(JavabloxException[] ez, int status) {
                for (JavabloxException e : ez)
                    switch (e.getCode()) {
                        case 1:
                            callback.onUserNotFound(e);
                            break;
                        case 0:
                            if (status == 500) callback.onUnknownError(e);
                            else callback.onAccessDenied(e);
                            break;
                        case 2:
                            callback.onPinLocked(e);
                            break;
                        case 3:
                            callback.onFeatureDisabled(e);
                    }
            }
        });
    }

    @Override
    public void getGender(JavabloxResponseCallback<Gender> callback) {
        svc.getGender().enqueue(new CallbackAdapter<>(callback) {
            @Override
            public Gender convertResponse(Gender response) {
                return response;
            }

            @Override
            public void onExceptions(JavabloxException[] ez, int status) {
                for (JavabloxException e : ez) {
                    if (e.getCode() == 0) callback.onAccessDenied(e);
                    else callback.onUserNotFound(e);
                }
            }
        });
    }

    @Override
    public void setGender(Gender gender, JavabloxResponseCallback<Void> callback) {
        svc.setGender(gender).enqueue(new CallbackAdapter<>(callback) {
            @Override
            public Void convertResponse(Void response) {
                return response;
            }

            @Override
            public void onExceptions(JavabloxException[] ez, int status) {
                for (JavabloxException e : ez)
                    switch (e.getCode()) {
                        case 1:
                            callback.onUserNotFound(e);
                            break;
                        case 6:
                            callback.onInvalidRequestBody(e);
                            break;
                        case 0:
                            if (status == 500) callback.onUnknownError(e);
                            else callback.onAccessDenied(e);
                            break;
                        case 2:
                            callback.onPinLocked(e);
                    }
            }
        });
    }

}