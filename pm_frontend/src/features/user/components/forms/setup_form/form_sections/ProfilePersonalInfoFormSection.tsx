import { Stack, TextInput } from "@mantine/core";
import type UserProfileType from "../../../../../../lib/types/entity_types/UserProfileType";

export default function ProfilePersonalInfoFormSection({
  formData,
  handleChange,
}: {
  formData: Partial<UserProfileType>;
  handleChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
}) {
  return (
    <Stack>
      <TextInput
        label="First Name"
        placeholder="Riley"
        name="firstName"
        value={formData.firstName}
        onChange={handleChange}
        type="text"
        withAsterisk
      />
      <TextInput
        label="Last Name"
        placeholder="Joseph"
        name="lastName"
        value={formData.lastName}
        onChange={handleChange}
        type="text"
        withAsterisk
      />
      <TextInput
        label="Phone Number"
        placeholder="(123) 456-7890"
        name="phoneNumber"
        value={formData.phoneNumber}
        onChange={handleChange}
        type="tel"
      />
    </Stack>
  );
}
