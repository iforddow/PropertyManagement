import { Avatar, Group, Menu, Text } from "@mantine/core";
import {
  IconLogout,
  IconMessage2Exclamation,
  IconQuestionMark,
  IconSettings,
  IconSun,
} from "@tabler/icons-react";
import RouteLink from "../../../components/buttons/RouteLink";
import useAuth from "../../../hooks/AuthenticationHook";
import { useNavigate } from "react-router-dom";
import { showErrorNotification } from "../../../lib/utils/NotificationManager";

export default function UserAvatarMenu() {
  const { logout } = useAuth();

  const navigate = useNavigate();

  const handleLogout = async () => {
    await logout()
      .then(() => {
        navigate("/auth", { replace: true });
      })
      .catch(() => {
        showErrorNotification(
          "Logout Failed",
          "An error occurred while trying to log out. Please try again.",
        );
      });
  };

  return (
    <Menu shadow="md" styles={{ dropdown: { minWidth: 300 } }}>
      <Menu.Target>
        <Avatar style={{ cursor: "pointer" }} />
      </Menu.Target>
      <Menu.Dropdown>
        <Menu.Label></Menu.Label>
        <Menu.Divider />
        <Menu.Item>
          <Group gap={"xs"}>
            <IconSettings size={20} />
            <Text size="sm" lh={1}>
              Settings & privacy
            </Text>
          </Group>
        </Menu.Item>
        <Menu.Item>
          <Group gap={"xs"}>
            <IconQuestionMark size={20} />
            <Text size="sm" lh={1}>
              Help & support
            </Text>
          </Group>
        </Menu.Item>
        <Menu.Item>
          <Group gap={"xs"}>
            <IconSun size={20} />
            <Text size="sm" lh={1}>
              Display & accessibility
            </Text>
          </Group>
        </Menu.Item>
        <Menu.Item>
          <RouteLink to="/report">
            <Group gap={"xs"}>
              <IconMessage2Exclamation size={20} />
              <Text size="sm" lh={1}>
                Give feedback
              </Text>
            </Group>
          </RouteLink>
        </Menu.Item>
        <Menu.Divider />
        <Menu.Item color="red" onClick={async () => await handleLogout()}>
          <Group gap={"xs"}>
            <IconLogout size={20} />
            <Text size="sm">Logout</Text>
          </Group>
        </Menu.Item>
      </Menu.Dropdown>
    </Menu>
  );
}
