import { BrowserRouter, Route, Routes } from "react-router-dom";
import HomePage from "../pages/HomePage";

/* 
The AppRouter component defines the main routing structure of the application.
It uses React Router to manage navigation between different pages.

@author IFD
@since 2025-06-27
*/
export default function AppRouter() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<HomePage />} />
      </Routes>
    </BrowserRouter>
  );
}
