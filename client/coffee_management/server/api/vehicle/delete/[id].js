import axios from "axios"

export default defineEventHandler(async (event) => {
    try {
        const {id} = event.context.params
        const vehicleID = id

        if(isNaN(vehicleID)){
            return "Id is missing!"
        }

        const res = await axios.delete(`http://127.0.0.1:8082/api/v1/car/${vehicleID}`)
        return res.data
    } catch (error) {
        console.log(error)
    }
})