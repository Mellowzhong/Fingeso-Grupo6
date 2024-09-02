<script setup>
import PropertyForm from '../components/PropertyForm.vue';
import { ref, computed } from 'vue';
import { postProperty, getCorredorList } from '../services/PropertyServices';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

const router = useRouter();
const store = useStore();

const user = computed(() => store.getters.getUsuario);
const corredorList = ref([]);

const obtenerListaCorredores = async () => {
  try {
    const result = await getCorredorList();
    if (result.success) {
      const corredores = result.data;
      console.log('Lista de corredores:', corredores);
      corredorList.value = corredores;
      // Aquí puedes realizar cualquier otra acción con la lista de corredores
    } else {
      console.error('Error al obtener la lista de corredores:', result.data);
    }
  } catch (error) {
    console.error('Error al obtener la lista de corredores:', error);
  }
};

const property = ref({
    userEmail: user.value.username,
    available: true,
    sale: false,
    price: '',
    address: '',
    type: '',
    rooms: '',
    bathrooms: '',
    squareMeters: '',
    yearConstruction: '',
    state: '',
    description: '',
    photos: [],
    services: '',
    parking: false,
    furnished: false,
    aprobated: false,
});

const saveProperty = async () => {
    const response = await postProperty(property.value);

    if (response.success) {
        //Actualizar el usuario en el store
        router.push('/');
        console.log('save user same time as property');
    } else {
        console.log('error saving property');
    }
};

obtenerListaCorredores();
</script>

<template>
    <div>
        <PropertyForm :property="property" :corredorList="corredorList" @save="saveProperty" :isEdit="false" />
    </div>
</template>