```jsx

const fetchSchedules = async (date, specializationId) => {
    const res = await fetch(
        `/api/v1/schedules/${date}/specializations/${specializationId}`
    );
    const data = await res.json();
    setSchedules(data); // Lưu kết quả vào state
};

```
