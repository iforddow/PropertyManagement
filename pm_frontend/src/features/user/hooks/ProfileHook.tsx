import { useEffect, useState } from "react";
import useAuth from "../../../hooks/AuthenticationHook";

export default function useProfile() {
  const { user, isAuthenticated } = useAuth();

  const [authProfileComplete, setAuthProfileComplete] = useState<
    boolean | null
  >(null);

  useEffect(() => {
    if (isAuthenticated && user) {
      setAuthProfileComplete(user.profileSetup);
    } else {
      setAuthProfileComplete(null);
    }
  }, [user, isAuthenticated]);

  return { authProfileComplete };
}
