package ch.hsr.mge.gadgeothek.service;

import android.util.Log;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.hsr.mge.gadgeothek.domain.Gadget;
import ch.hsr.mge.gadgeothek.domain.Loan;
import ch.hsr.mge.gadgeothek.domain.Reservation;

public class LibraryService {
    
    private static final String TAG = LibraryService.class.getSimpleName();
    private static LoginToken token;
    private static String serverUrl;

    public static void setServerAddress(String address) {
        Log.d(TAG, "Setting server to " + address);
        serverUrl = address;
    }

    public static boolean isLoggedIn() {
        return token != null;
    }

    public static void login(String mail, String password, final Callback<Boolean> callback) {
        HashMap<String, String> parameter = new HashMap<>();
        parameter.put("email", mail);
        parameter.put("password", password);
        Request<LoginToken> request = new Request<>(HttpVerb.POST, serverUrl + "/login", LoginToken.class, null, parameter, new Callback<LoginToken>() {
            @Override
            public void onCompletion(LoginToken input) {
                token = input;
                callback.onCompletion(input != null && !input.getSecurityToken().isEmpty());
            }

            @Override
            public void onError(String message) {
                callback.onError(message);
            }
        });
        request.execute();
    }

    public static void logout(final Callback<Boolean> callback) {
        HashMap<String, String> headers = getAuthHeaders();

        Request<Boolean> request = new Request<>(HttpVerb.POST, serverUrl + "/logout", Boolean.class, headers, null, new Callback<Boolean>() {
            @Override
            public void onCompletion(Boolean input) {
                if (input) {
                    token = null;
                }
                callback.onCompletion(input);
            }

            @Override
            public void onError(String message) {
                token = null;
                callback.onError(message);
            }
        });
        request.execute();
    }

    public static void register(String mail, String password, String name, String studentenNumber, final Callback<Boolean> callback) {
        HashMap<String, String> parameter = new HashMap<>();
        parameter.put("email", mail);
        parameter.put("password", password);
        parameter.put("name", name);
        parameter.put("studentnumber", studentenNumber);

        Request<Boolean> request = new Request<>(HttpVerb.POST, serverUrl + "/register", Boolean.class, null, parameter, new Callback<Boolean>() {
            @Override
            public void onCompletion(Boolean input) {
                callback.onCompletion(input);
            }

            @Override
            public void onError(String message) {
                callback.onError(message);
            }
        });
        request.execute();
    }


    public static void getLoansForCustomer(final Callback<List<Loan>> callback) {
        if (token == null) {
            throw new IllegalStateException("Not logged in");
        }
        HashMap<String, String> headers = getAuthHeaders();

        Request<List<Loan>> request = new Request<>(HttpVerb.GET, serverUrl + "/loans", new TypeToken<List<Loan>>() {
        }.getType(), headers, null, new Callback<List<Loan>>() {
            @Override
            public void onCompletion(List<Loan> input) {
                callback.onCompletion(input == null ? new ArrayList<Loan>() : input);
            }

            @Override
            public void onError(String message) {
                callback.onError(message);
            }
        });
        request.execute();
    }

    public static void getReservationsForCustomer(final Callback<List<Reservation>> callback) {
        if (token == null) {
            throw new IllegalStateException("Not logged in");
        }
        HashMap<String, String> headers = getAuthHeaders();

        Request<List<Reservation>> request = new Request<>(HttpVerb.GET, serverUrl + "/reservations", new TypeToken<List<Reservation>>() {
        }.getType(), headers, null, new Callback<List<Reservation>>() {
            @Override
            public void onCompletion(List<Reservation> input) {
                callback.onCompletion(input == null ? new ArrayList<Reservation>() : input);
            }

            @Override
            public void onError(String message) {
                callback.onError(message);
            }
        });
        request.execute();
    }


    public static void reserveGadget(Gadget toReserve, final Callback<Boolean> callback) {
        if (token == null) {
            throw new IllegalStateException("Not logged in");
        }
        HashMap<String, String> headers = getAuthHeaders();

        HashMap<String, String> parameter = new HashMap<>();
        parameter.put("gadgetId", toReserve.getInventoryNumber());

        Request<Boolean> request = new Request<>(HttpVerb.POST, serverUrl + "/reservations", new TypeToken<Boolean>() {
        }.getType(), headers, parameter, new Callback<Boolean>() {
            @Override
            public void onCompletion(Boolean success) {
                callback.onCompletion(success);
            }

            @Override
            public void onError(String message) {
                callback.onError(message);
            }
        });
        request.execute();
    }


    public static void deleteReservation(Reservation toDelete, final Callback<Boolean> callback) {
        if (token == null) {
            throw new IllegalStateException("Not logged in");
        }
        HashMap<String, String> headers = getAuthHeaders();

        HashMap<String, String> parameter = new HashMap<>();
        parameter.put("id", toDelete.getReservationId());

        Request<Boolean> request = new Request<>(HttpVerb.DELETE, serverUrl + "/reservations", Boolean.class, headers, parameter, new Callback<Boolean>() {
            @Override
            public void onCompletion(Boolean input) {
                callback.onCompletion(input);
            }

            @Override
            public void onError(String message) {
                callback.onError(message);
            }
        });
        request.execute();
    }

    public static void getGadgets(final Callback<List<Gadget>> callback) {
        if (token == null) {
            throw new IllegalStateException("Not logged in");
        }
        HashMap<String, String> headers = getAuthHeaders();

        Request<List<Gadget>> request = new Request<>(HttpVerb.GET, serverUrl + "/gadgets", new TypeToken<List<Gadget>>() {
        }.getType(), headers, null, new Callback<List<Gadget>>() {
            @Override
            public void onCompletion(List<Gadget> input) {
                callback.onCompletion(input);
            }

            @Override
            public void onError(String message) {
                callback.onError(message);
            }
        });
        request.execute();
    }

    private static HashMap<String, String> getAuthHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("x-security-token", token.getSecurityToken());
        headers.put("x-customer-id", token.getCustomerId());
        return headers;
    }
}


