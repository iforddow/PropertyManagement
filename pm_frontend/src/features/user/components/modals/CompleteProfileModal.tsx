import { modals } from "@mantine/modals";
import useProfile from "../../hooks/ProfileHook";
import { useEffect } from "react";
import { Anchor, Box, Button, Text, Title, Space } from "@mantine/core";
import HouseLogo from "../../../../components/images/HouseLogo";
import UserSetupForm from "../forms/setup_form/UserSetupForm";

export default function CompleteProfileModal({
  required = false,
}: {
  required?: boolean;
}) {
  const { authProfileComplete } = useProfile();

  const devForceKey = import.meta.hot?.data?.timestamp ?? Date.now();

  useEffect(() => {
    if (authProfileComplete === false) {
      // In development, close all modals before opening a new one
      modals.open({
        closeOnEscape: false,
        closeOnClickOutside: false,
        withCloseButton: false,
        radius: "xl",
        children: (
          <Box
            style={{
              display: "flex",
              flexDirection: "column",
              alignItems: "center",
            }}
            maw={300}
            p={"lg"}
          >
            <HouseLogo width={80} height={80} />
            <Space h="md" />
            <Title mb="md" ta="center" lh={1}>
              {required ? "Complete Your Profile" : "Thanks for signing up!"}
            </Title>
            <Text size="sm" ta="center" c={"dimmed"} fs={"italic"}>
              {required
                ? "Please complete your profile to access all features."
                : "We recommend completing your profile to get the most out of our platform."}
            </Text>
            <Space h="md" />
            <Button
              onClick={() => {
                modals.open({
                  centered: true,
                  closeOnEscape: false,
                  closeOnClickOutside: false,
                  withCloseButton: false,
                  radius: "xl",
                  size: "",
                  children: (
                    <Box
                      style={{
                        display: "flex",
                        flexDirection: "column",
                        alignItems: "center",
                      }}
                      p={"lg"}
                    >
                      <UserSetupForm />
                      <Anchor
                        size="xs"
                        variant="text"
                        mt={"lg"}
                        c={"dimmed"}
                        onClick={() => modals.closeAll()}
                      >
                        I'll finish this later
                      </Anchor>
                    </Box>
                  ),
                });
              }}
            >
              Complete Profile
            </Button>
            <Space h="md" />
            <Anchor
              size="xs"
              variant="text"
              onClick={() => modals.closeAll()}
              c={"dimmed"}
            >
              No thanks, I'll do it later
            </Anchor>
          </Box>
        ),
        size: "auto",
        centered: true,
      });
    }
  }, [authProfileComplete, devForceKey]);

  return null;
}
