import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],

  server: {
    proxy: {
      "/api/v1/admin/doctors": {
        target: "http://localhost:8070",
        changeOrigin: true,
        secure: false
      },
      "/api/v1/admin/specializations": {
        target: "http://localhost:8070",
        changeOrigin: true,
        secure: false
      },
      "/api/v1/admin/clinics": {
        target: "http://localhost:8071",
        changeOrigin: true,
        secure: false
      },
      "/api/v1/clinics": {
        target: "http://localhost:8071",
        changeOrigin: true,
        secure: false
      },
      "/api/v1/admin/schedules": {
        target: "http://localhost:8072",
        changeOrigin: true,
        secure: false
      },
      "/api/v1/schedules": {
        target: "http://localhost:8072",
        changeOrigin: true,
        secure: false
      },
      "/api/v1/doctors": {
        target: "http://localhost:8070",
        changeOrigin: true,
        secure: false
      },
      "/api/v1/specializations": {
        target: "http://localhost:8070",
        changeOrigin: true,
        secure: false
      },
      "/api/v1/appointments": {
        target: "http://localhost:8073",
        changeOrigin: true,
        secure: false
      },
    }
  }
})