<template>
  <v-form class="mt-1" ref="additionalform">
    <v-container>
      <v-row>
        <v-col cols="12" md="3" class="py-0 ma-0 my-0">
          <input type="hidden" :value="model.id" />
          <v-checkbox
            class="ma-0 pa-0 form-label"
            v-model="model.enabled"
            label="Ativo"
            type="checkbox"
            dense
            outlined
          />
        </v-col>
        <v-col cols="12" md="9" class="py-0 ma-0 my-0">
          <v-select
            v-model="model.requiredType"
            :items="requiredTypes"
            :item-text="'description'"
            class="ma-0 pa-0 form-label"
            label="Obrigatoriedade"
            return-object
            required
            dense
            outlined
            :rules="[required('Obrigatoriedade')]"
          />
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="12" md="3" class="py-0 ma-0 my-0">
          <CurrencyField
            v-model="model.value"
            valueWhenIsEmpty="0"
            label="Valor"
            class="ma-0 pa-0 form-label"
            dense
            outlined
            required
            :rules="[required('Valor')]"
          />
        </v-col>
        <v-col cols="12" md="9" class="py-0 ma-0 my-0">
          <v-text-field
            v-model="model.name"
            label="Descrição"
            class="ma-0 pa-0 form-label"
            dense
            outlined
            required
            counter="80"
            :rules="[
              required('Descrição'),
              minLength('Descrição', 2),
              maxLength('Descrição', 200),
            ]"
          />
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="12" class="py-0 ma-0 my-0">
          <v-textarea
            v-model="model.additionalPackage"
            label="Adicional Múltiplo"
            :auto-grow="true"
            class="ma-0 pa-0 form-label"
            rows="4"
            clearable
            dense
            outlined
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
              if (!this.$refs.additionalform.validate()) return;
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
import { LOAD_ADDITIONAL_REQUIRED_TYPES } from "@/store/_actiontypes";
import CurrencyField from "../../components/textfield/CurrencyField.vue";

export default {
  components: { CurrencyField },
  props: {
    additional: {
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
    this.$store.dispatch(
      `additionalRequiredTypes/${LOAD_ADDITIONAL_REQUIRED_TYPES}`
    );
  },
  computed: {
    ...mapState({
      requiredTypes: (state) =>
        state.additionalRequiredTypes.additionalRequiredTypes,
    }),
    model: {
      get() {
        return this.additional;
      },
      set(additional) {
        this.$emit("input", additional);
      },
    },
  },
  methods: {
    reset() {
      this.$refs.additionalform.reset();
    },
  },
};
</script>

<style>
</style>