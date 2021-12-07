<template>
  <v-form class="mt-1" ref="placeform">
    <v-container>
      <v-row>
        <v-col cols="12" md="3" class="py-0 ma-0 my-1">
          <input type="hidden" :value="model.id" />
          <v-checkbox
            class="ma-0 pa-0 form-label"
            v-model="model.enabled"
            label="Ativo"
            type="checkbox"
          ></v-checkbox>
        </v-col>
        <v-col cols="12" md="9" class="py-0 ma-0 my-1">
          <v-text-field
            v-model="model.name"
            label="Nome"
            class="ma-0 pa-0 form-label"
            dense
            required
            counter="80"
            :rules="[
              required('Nome'),
              minLength('Nome', 2),
              maxLength('Nome', 80),
            ]"
          />
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="12" md="3" class="py-0 ma-0 my-1">
          <CurrencyField
            v-model="model.value"
            valueWhenIsEmpty="0"
            label="Valor"
            class="ma-0 pa-0 form-label"
            dense
            required
            :rules="[required('Valor')]"
          />
        </v-col>
        <v-col cols="12" md="9" class="py-0 ma-0 my-0">
          <v-select
            v-model="model.leaseType"
            :items="leaseTypes"
            :item-text="'description'"
            class="ma-0 pa-0 form-label"
            label="Tipo de locação"
            return-object
            required
            :rules="[required('Tipo de locação')]"
          />
        </v-col>
      </v-row>
      <v-row class="justify-end">
        <v-btn
          v-if="showCloseButton"
          outlined
          small
          class="mr-2 red--text font-weight-bold"
          @click="onCloseClick"
          >Cancelar</v-btn
        >
        <v-btn
          outlined
          small
          class="primary--text font-weight-bold"
          :loading="loading"
          @click="
            () => {
              if (!this.$refs.placeform.validate()) return;
              onSubmitClick();
            }
          "
          >Salvar</v-btn
        >
      </v-row>
    </v-container>
  </v-form>
</template>

<script>
import { mapState } from "vuex";
import validations from "@/helpers/validations";
import { LOAD_LEASE_TYPES } from "@/store/_actiontypes";
import CurrencyField from "../../components/textfield/CurrencyField.vue";

export default {
  components: { CurrencyField },
  props: {
    place: {
      type: Object,
    },
    showCloseButton: {
      type: Boolean,
      default: false,
    },
    onSubmitClick: {
      type: Function,
    },
    onCloseClick: {
      type: Function,
    },
    loading: {
      type: Boolean,
    },
  },
  data() {
    return {
      ...validations,
    };
  },
  mounted() {
    this.$store.dispatch(`leaseTypes/${LOAD_LEASE_TYPES}`);
  },
  computed: {
    ...mapState({
      leaseTypes: (state) => state.leaseTypes.leaseTypes,
    }),
    model: {
      get() {
        return this.place;
      },
      set(place) {
        this.$emit("input", place);
      },
    },
  },
  methods: {
    reset() {
      this.$refs.placeform.reset();
    },
  },
};
</script>

<style>
</style>