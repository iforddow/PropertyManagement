import { Button } from "@mantine/core";
import {
  showErrorNotification,
  showInfoNotification,
  showSuccessNotification,
  showWarningNotification,
} from "../../lib/utils/NotificationManager";

/* 
The HomePage component serves as the main entry point of the application.

@author IFD
@since 2025-06-27
*/
export default function HomePage() {
  return (
    <div>
      <h1>Welcome to the Home Page</h1>
      <p>This is the main entry point of the application.</p>
      <Button
        variant="gradient"
        onClick={() => {
          showErrorNotification();
          showSuccessNotification();
          showWarningNotification();
          showInfoNotification();
        }}
      >
        Hello
      </Button>
    </div>
  );
}
