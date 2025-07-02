import { Button, Center, SimpleGrid, Stack, Text } from "@mantine/core";
import { IconKey, IconHomeCog } from "@tabler/icons-react";
import type UserProfileType from "../../../../../../lib/types/entity_types/UserProfileType";

export default function ProfileTypeFormSection({
  setFormData,
  setActiveStep,
}: {
  setFormData: React.Dispatch<React.SetStateAction<Partial<UserProfileType>>>;
  setActiveStep: React.Dispatch<React.SetStateAction<number>>;
}) {
  return (
    <SimpleGrid cols={2} spacing={"md"}>
      <Center>
        <Button
          h={150}
          w={150}
          variant="default"
          onClick={() => {
            setFormData((prev) => ({
              ...prev,
              profileType: "tenant",
            }));
            setActiveStep(1);
          }}
        >
          <Stack align="center" justify="center" gap={10}>
            <IconKey size={40} />
            <Text size="sm" style={{ textWrap: "wrap" }}>
              Renter
            </Text>
          </Stack>
        </Button>
      </Center>
      <Center>
        <Button
          h={150}
          w={150}
          variant="default"
          onClick={() => {
            setFormData((prev) => ({
              ...prev,
              profileType: "propertyManager",
            }));
            setActiveStep(1);
          }}
        >
          <Stack align="center" justify="center" gap={10}>
            <IconHomeCog size={40} />
            <Text size="sm" style={{ textWrap: "wrap" }}>
              Property Manager
            </Text>
          </Stack>
        </Button>
      </Center>
    </SimpleGrid>
  );
}
