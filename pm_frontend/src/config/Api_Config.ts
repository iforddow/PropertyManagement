export const PM_API_BASE_URL = import.meta.env.VITE_AUTH_API_BASE_URL;
export const PM_IMAGE_BASE_URL = import.meta.env.VITE_AUTH_BASE_IMAGES_URL;

/* 
A collection of API endpoints for the Property Management application.

@author IFD
@since 2025-06-27
*/
export const PM_API_ENDPOINTS = {
  AUTH: {
    LOGIN: `${PM_API_BASE_URL}/login`,
    REGISTER: `${PM_API_BASE_URL}/register`,
    LOGOUT: `${PM_API_BASE_URL}/logout`,
    REFRESH: `${PM_API_BASE_URL}/refresh`,
  },
};
