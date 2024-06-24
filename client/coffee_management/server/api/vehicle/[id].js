import axios from "axios";

export default defineEventHandler(async (event) => {
  try {
    const { id } = event.context.params;
    const vehicleID = Number(id);
    if (isNaN(vehicleID)) {
      return "Missing id!";
    }
    const res = await axios.get(
      `http://127.0.0.1:8082/api/v1/car/${vehicleID}`
    );
    return res.data;
  } catch (error) {}
});
