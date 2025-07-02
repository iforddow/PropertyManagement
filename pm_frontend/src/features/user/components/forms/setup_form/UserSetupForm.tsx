import React, { useState } from "react";
import type UserProfileType from "../../../../../lib/types/entity_types/UserProfileType";
import { Button, Space, Title, Text, Group } from "@mantine/core";
import { IconArrowLeft, IconArrowRight } from "@tabler/icons-react";
import ProfileTypeFormSection from "./form_sections/ProfileTypeFormSection";
import ProfilePersonalInfoFormSection from "./form_sections/ProfilePersonalInfoFormSection";

export default function UserSetupForm() {
  const [activeStep, setActiveStep] = useState(0);

  const [formData, setFormData] = useState<Partial<UserProfileType>>({
    firstName: "",
    lastName: "",
    phoneNumber: "",
    profilePictureUrl: "",
    profileType: "tenant",
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value, type, checked } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  return (
    <form>
      <Title mb="xs" ta="left" w={"100%"}>
        Step {activeStep + 1} of 3:
      </Title>
      <Text size="md" ta="left">
        {activeStep === 0
          ? "Select a profile type."
          : activeStep === 1
          ? "Please fill out some personal information."
          : "Review and confirm your details."}
      </Text>
      <Space h="md" />
      {activeStep == 0 && (
        <ProfileTypeFormSection
          setFormData={setFormData}
          setActiveStep={setActiveStep}
        />
      )}
      {activeStep == 1 && (
        <ProfilePersonalInfoFormSection
          formData={formData}
          handleChange={handleChange}
        />
      )}
      <Space h="sm" />
      {activeStep > 0 && (
        <Group justify="space-between" align="center">
          <Button
            variant="transparent"
            p={0}
            onClick={() => {
              if (activeStep !== 0) {
                setActiveStep(activeStep - 1);
              }
            }}
          >
            <Group gap={5} justify="space-between">
              <IconArrowLeft size={20} />
              <Text size="sm">Back</Text>
            </Group>
          </Button>
          {activeStep > 0 && activeStep < 2 && (
            <Button
              variant="transparent"
              p={0}
              onClick={() => {
                if (activeStep !== 2) {
                  setActiveStep(activeStep + 1);
                }
              }}
            >
              <Group gap={5} justify="space-between">
                <Text size="sm">Next</Text>
                <IconArrowRight size={20} />
              </Group>
            </Button>
          )}
        </Group>
      )}
    </form>
  );
}
