import { Image } from "@mantine/core";

export default function HouseLogo({
  height = 35,
  width = "auto",
  fit = "contain",
}: {
  height?: string | number;
  width?: string | number;
  fit?: "contain" | "cover" | "fill" | "none" | "scale-down";
}) {
  return <Image src={"/zeedyHouse.png"} h={height} w={width} fit={fit} />;
}
